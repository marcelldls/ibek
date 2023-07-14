"""
Functions for generating an IocInstance derived class from a
support module definition YAML file
"""
from __future__ import annotations

import builtins
from typing import Any, Dict, Literal, Sequence, Tuple, Type, Union

from pydantic import Field, RootModel, create_model, field_validator, model_validator
from pydantic.fields import FieldInfo

from .globals import BaseSettings, render_with_utils
from .support import Definition, IdArg, ObjectArg, Support

id_to_entity: Dict[str, Entity] = {}


class Entity(BaseSettings):
    """
    A baseclass for all generated Entity classes.
    """

    type: str = Field(description="The type of this entity")
    entity_enabled: bool = Field(
        description="enable or disable this entity instance", default=True
    )
    __definition__: Definition

    @model_validator(mode="after")  # type: ignore
    def add_ibek_attributes(cls, entity: Entity):
        """Whole Entity model validation"""

        # find the id field in this Entity if it has one
        ids = set(a.name for a in entity.__definition__.args if isinstance(a, IdArg))

        entity_dict = entity.model_dump()
        for arg, value in entity_dict.items():
            if arg in ids:
                # add this entity to the global id index
                if value in id_to_entity:
                    raise ValueError(f"Duplicate id {value} in {list(id_to_entity)}")
                id_to_entity[value] = entity
            elif isinstance(value, str):
                # Jinja expansion of any of the Entity's string args/values
                setattr(entity, arg, render_with_utils(entity_dict, value))
        return entity


def make_entity_model(definition: Definition, support: Support) -> Type[Entity]:
    """
    Create an Entity Model from a Definition instance and a Support instance.
    """

    def add_arg(name, typ, description, default):
        args[name] = (
            typ,
            FieldInfo(description=description, default=default),
        )

    args: Dict[str, Tuple[type, Any]] = {}
    validators: Dict[str, Any] = {}

    # fully qualified name of the Entity class including support module
    full_name = f"{support.module}.{definition.name}"

    # add in each of the arguments as a Field in the Entity
    for arg in definition.args:
        full_arg_name = f"{full_name}.{arg.name}"
        arg_type: Any

        if isinstance(arg, ObjectArg):

            @field_validator(arg.name, mode="after")
            def lookup_instance(cls, id):
                try:
                    return id_to_entity[id]
                except KeyError:
                    raise KeyError(f"object {id} not found in {list(id_to_entity)}")

            validators[full_arg_name] = lookup_instance
            arg_type = object

        elif isinstance(arg, IdArg):
            arg_type = str

        else:
            # arg.type is str, int, float, etc.
            arg_type = getattr(builtins, arg.type)

        default = getattr(arg, "default", None)
        add_arg(arg.name, arg_type, arg.description, default)

    # add in the calculated values Jinja Templates as Fields in the Entity
    for value in definition.values:
        add_arg(value.name, str, value.description, value.value)

    # add the type literal which discriminates between the different Entity classes
    typ = Literal[full_name]  # type: ignore
    add_arg("type", typ, "The type of this entity", full_name)

    entity_cls = create_model(
        full_name.replace(".", "_"),
        **args,
        __validators__=validators,
        __base__=Entity,
    )  # type: ignore

    # add a link back to the Definition Instance that generated this Entity Class
    entity_cls.__definition__ = definition

    return entity_cls


def make_entity_models(support: Support):
    """
    Create Entity subclasses for all Definition instances in the given
    Support instance. Returns a list of the Entity subclasses Models.
    """

    entity_models = []
    entity_names = []

    for definition in support.defs:
        entity_models.append(make_entity_model(definition, support))
        if definition.name in entity_names:
            raise ValueError(f"Duplicate entity name {definition.name}")
        entity_names.append(definition.name)

    return entity_models


def make_ioc_model(entity_models: Sequence[Type[Entity]]) -> Type[IOC]:
    """
    Create an IOC derived model, by setting its entities attribute to
    be of type 'list of Entity derived classes'.
    """

    class EntityModel(RootModel):
        root: Union[tuple(entity_models)] = Field(discriminator="type")  # type: ignore

    class NewIOC(IOC):
        entities: Sequence[EntityModel] = Field(  # type: ignore
            description="List of entities this IOC instantiates"
        )

    return NewIOC


def clear_entity_model_ids():
    """Resets the global id_to_entity dict."""

    id_to_entity.clear()


class IOC(BaseSettings):
    """
    Used to load an IOC instance entities yaml file into a Pydantic Model.
    """

    ioc_name: str = Field(description="Name of IOC instance")
    description: str = Field(description="Description of what the IOC does")
    generic_ioc_image: str = Field(
        description="The generic IOC container image registry URL"
    )
    # this will be replaced in derived classes made by make_ioc_model
    entities: Sequence[Entity]
