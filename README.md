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
