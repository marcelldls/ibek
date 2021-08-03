import json
from pathlib import Path
from typing import Optional

import typer
from apischema.json_schema import deserialization_schema
from ruamel.yaml import YAML
from ruamel.yaml.main import YAML

from ibek import __version__
from ibek.dataclass_from_yaml import yaml_to_dataclass
from ibek.helm import create_boot_script, create_helm
from ibek.support import Support

app = typer.Typer()
yaml = YAML()


def version_callback(value: bool):
    if value:
        typer.echo(__version__)
        raise typer.Exit()


@app.callback()
def main(
    version: Optional[bool] = typer.Option(
        None,
        "--version",
        callback=version_callback,
        is_eager=True,
        help="Print the version of ibek and exit",
    )
):
    """Do 3 things..."""


@app.command()
def ibek_schema(
    output: Path = typer.Argument(..., help="The filename to write the schema to")
):
    """Produce the JSON schema for a <support_module>.ibek.yaml file"""
    schema = json.dumps(deserialization_schema(Support), indent=2)
    with open(output, "w") as f:
        f.write(schema)


@app.command()
def ioc_schema(
    description: Path = typer.Argument(
        ..., help="The filepath to read the IOC class description from"
    ),
    output: Path = typer.Argument(..., help="The filename to write the schema to"),
):
    """Create a json schema from a <support_module>.ibek.yaml file"""

    # create a support object graph from YAML
    support = yaml_to_dataclass(str(str(description)))

    # populate its dataclass namespace
    ioc_class = support.get_module_dataclass()

    schema = json.dumps(deserialization_schema(ioc_class), indent=2)
    with open(output, "w") as f:
        f.write(schema)


@app.command()
def build_ioc(
    description: Path = typer.Argument(
        ..., help="The filepath to read the IOC class description from"
    ),
    definition: Path = typer.Argument(
        ..., help="The filepath to read the IOC instance definition from"
    ),
    out: Path = typer.Argument(
        default="iocs", help="Path in which to build the helm chart"
    ),
):
    """Build a startup script, database and Helm chart from <ioc>.yaml"""
    boot_script_path = create_helm(name="TODO_Dummy", path=out)

    create_boot_script(
        ioc_yaml=definition, save_file=boot_script_path, ioc_class_ibek_yaml=description
    )


# test with:
#     pipenv run python -m ibek
if __name__ == "__main__":
    app()
