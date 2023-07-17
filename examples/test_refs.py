from __future__ import annotations

from typing import Dict, List, Optional

from pydantic import BaseModel, ConfigDict, Field, field_validator, model_validator

id_to_entity: Dict[str, Entity] = {}


class Entity(BaseModel):
    name: str = Field(..., description="The name of this entity")
    value: str = Field(..., description="The value of this entity")
    ref: Optional[str] = Field(
        default=None, description="Reference another Entity name"
    )
    model_config = ConfigDict(extra="forbid")

    @model_validator(mode="after")  # type: ignore
    def add_ibek_attributes(cls, entity: Entity):
        id_to_entity[entity.name] = entity

        return entity

    @field_validator("ref", mode="after")
    def lookup_instance(cls, id):
        try:
            return id_to_entity[id]
        except KeyError:
            raise KeyError(f"object {id} not found in {list(id_to_entity)}")


class Entities(BaseModel):
    entities: List[Entity] = Field(..., description="The entities in this model")


model1 = Entities(
    **{
        "entities": [
            {"name": "one", "value": "OneValue"},
            {"name": "two", "value": "TwoValue", "ref": "one"},
        ]
    }
)

# demonstrate that entity one has a reference to entity two
assert model1.entities[1].ref.value == "OneValue"

# this should throw an error because entity one has illegal arguments
model2 = Entities(
    **{
        "entities": [
            {"name": "one", "value": "OneValue", "illegal": "bad argument"},
            {"name": "two", "value": "TwoValue", "ref": "one"},
        ]
    }
)
