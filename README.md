# Experimenting OptaPlanner with class scheduling problem

This experimenting is based on the examples of the official OptaPlanner Quickstarts repository:

- [OptaPlanner Quickstarts School Timetabling Example](https://github.com/kiegroup/optaplanner-quickstarts/tree/stable/use-cases/school-timetabling#readme)

Here are other useful links:

- [OptaPlanner Repository](https://github.com/kiegroup/optaplanner)
- [OptaPlanner Quickstarts Repository](https://github.com/kiegroup/optaplanner-quickstarts/)

## Problem description

The problem is to assign classes to rooms and timeslots, while avoiding conflicts between classes that share a student group, teacher or room.

## Changes

I have made the following changes to the original example:

- Add Lombok to the project
- Starts without a web server
- Create LoggerFile() class to display reports
- Create Containts() class to configure the project
- Create ConstraintsUsed() enum to configure the constraints
- Rename Physical Education to P.E. in DemoDataGenerator() to not break the report

## Running the application

To run the application, execute the following command:

```bash

# Run the application
# OBS: Changes Constants.java to change the settings
mvn clean compile -DskipTests -q

# Run all combinations of settings
./run.ps1 # Windows
python scripts/cli.py run-combinations

# Generate groups of same reports to be compared
python scripts/cli.py group-same-files --input-dir reports > group_same_files.txt

```

## Results

For `demoData = SMALL`, the reports consistently display identical results for each set of `constraintsUsed` across various `terminationSpentLimit` values.
In the case of `demoData = LARGE`, only reports associated with `constraintsUsed = ONLY_HARD` exhibit uniformity across different `terminationSpentLimit` values.
This pattern suggests that the algorithm might have already converged to a local optimal solution based on these parameters.

Upon examining the execution with `demoData = LARGE`, `constraintsUsed = ALL`, and `terminationSpentLimit = 1 minute`, it becomes evident that the algorithm tends to group some classes in the same room, although not universally. When examining this result, it is apparent that manual adjustments to certain classes could lead to an improved solution.

Although the documentation recommends running for at least 5 minutes to obtain a reliable solution, this experiment specifically aimed to observe the algorithm's behavior within a short timeframe.

## TODO

- [ ] Add more constraints
