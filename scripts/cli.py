import argparse
from pathlib import Path
from typing import Callable
from ms import ms_parse
import json
import subprocess
import hashlib


def cmd_run(args):
    print("CMD:", args)
    subprocess.run(args, check=True, shell=True)


def duration_parse(text: str) -> str:
    if text.startswith("Duration."):
        return text
    milliseconds = ms_parse(text)
    return f"Duration.ofMillis({milliseconds}); // {text}"


def command_build_command_template(args: argparse.Namespace) -> str:
    """
    Build the command template and print it to output [default: stdout]
    """
    template_path = Path(__file__).parent / "Constants.java.template"
    template = template_path.read_text()
    template = template.replace("{{demoData}}", args.demoData.upper())
    template = template.replace("{{terminationSpentLimit}}", duration_parse(args.terminationSpentLimit))
    template = template.replace("{{constraintsUsed}}", args.constraintsUsed.upper())
    args.output.write(template)


def command_group_same_files(args: argparse.Namespace) -> str:
    """
    Group files with the same content
    """

    input_dir = Path(args.input_dir)
    files = {}
    for path in input_dir.glob("*"):
        if path.is_file():
            hash_ = path.read_bytes()
            hash_ = hashlib.sha256(hash_).hexdigest()
            files.setdefault(hash_, []).append(path)

    print("Groups:")
    for hash_,  paths in files.items():
        print(f"  Hash {hash_}:")
        for path in paths:
            print(f"    {path}")


def command_run_combinations(args: argparse.Namespace) -> str:
    """
    Run all combinations based on the combinations file
    """
    combinations = json.loads(Path(args.combinations or (Path(__file__).parent / "combinations.json")).read_text())

    for time in combinations["terminationSpentLimit"]:
        for data in combinations["demoData"]:
            for constraint in combinations["constraintsUsed"]:
                report_path = Path(f"reports/{data}_{constraint}_pt{time}.log")
                if report_path.exists():
                    print(f"INFO: Skip {report_path}")
                else:
                    cmd_run(["python", __file__, "build-template", "--demoData", data, "--terminationSpentLimit", time, "--constraintsUsed", constraint, "--output", "src/main/java/com/ipb/Constants.java"])
                    cmd_run(["mvn", "exec:java", "-q"])
                print()
    

def get_parser():
    def from_command(func: Callable) -> argparse.ArgumentParser:
        prefix = "command_"
        name = func.__name__.replace(prefix, "")
        name = name.replace("_", "-")
        sp = subparser.add_parser(name, help=func.__doc__)
        sp.set_defaults(func=func)
        return sp

    parser = argparse.ArgumentParser()
    subparser = parser.add_subparsers(dest="command")

    sp = from_command(command_build_command_template)
    sp.add_argument("--demoData", "-d", required=True, choices=["small", "large"], help="Demo data to use")
    sp.add_argument("--terminationSpentLimit", "-t", required=True, help="Termination spent limit")
    sp.add_argument("--constraintsUsed", "-c", required=True, choices=["only_hard", "with_soft_1", "with_soft_2", "with_soft_3", "all"], help="Constraints used")
    sp.add_argument("--output", "-o", type=argparse.FileType("w"), default="-", help="Output file")

    sp = from_command(command_group_same_files)
    sp.add_argument("--input-dir", "-i", required=True, help="Input directory")

    sp = from_command(command_run_combinations)
    sp.add_argument("--combinations", "-c", help="Combinations file")

    return parser


if __name__ == '__main__':
    parser = get_parser()
    options, args = parser.parse_known_args()
    if options.command is None:
        parser.print_help()
    else:
        options.func(options)
