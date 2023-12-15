package com.ipb;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import com.ipb.demo.DemoDataGenerator;
import com.ipb.demo.DemoDataGenerator.DemoData;
import com.ipb.domain.Lesson;
import com.ipb.domain.Room;
import com.ipb.domain.TimeTable;
import com.ipb.domain.Timeslot;
import com.ipb.persistence.LessonRepository;
import com.ipb.persistence.RoomRepository;
import com.ipb.persistence.TimeslotRepository;
import com.ipb.solver.TimeTableConstraintProvider;

// https://github.com/kiegroup/optaplanner-quickstarts/blob/8.x/use-cases/school-timetabling/src/main/java/org/acme/schooltimetabling/solver/TimeTableConstraintProvider.java
// https://github.com/kiegroup/optaplanner-quickstarts/blob/8.x/hello-world/src/main/java/org/acme/schooltimetabling/TimeTableApp.java

public class App {
    static ILogger logger = new Logger();
    static DemoData demoData = Constants.demoData;
    static long startTime, endTime;

    public static void main(String[] args) {
        TimeslotRepository timeslotRepository = new TimeslotRepository();
        RoomRepository roomRepository = new RoomRepository();
        LessonRepository lessonRepository = new LessonRepository();
        DemoDataGenerator dataGenerator = new DemoDataGenerator(demoData, timeslotRepository, roomRepository,
                lessonRepository);
        dataGenerator.generate();

        List<Timeslot> timeslotList = timeslotRepository.getAll();
        List<Room> roomList = roomRepository.getAll();
        List<Lesson> lessonList = lessonRepository.getAll();
        TimeTable problem = new TimeTable(timeslotList, roomList, lessonList);

        logger.info("Solving time table...");

        startTime = System.currentTimeMillis();
        SolverFactory<TimeTable> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(TimeTable.class)
                .withEntityClasses(Lesson.class)
                .withConstraintProviderClass(TimeTableConstraintProvider.class)
                // The solver runs only for 5 seconds on this small dataset.
                // It's recommended to run for at least 5 minutes ("5m") otherwise.
                .withTerminationSpentLimit(Constants.terminationSpentLimit));
        endTime = System.currentTimeMillis();

        logger.info("SolverFactory created in " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        TimeTable solution = solverFactory.buildSolver().solve(problem);
        endTime = System.currentTimeMillis();

        printTimeTable(solution);

        logger.info("Solved in " + (endTime - startTime) + "ms");
        logger.close();
    }

    public static void printTimeTable(TimeTable timeTable) {
        ILogger logger = LoggerFile.fromConstants();
        logger.info("");
        List<Room> roomList = timeTable.getRoomList();
        List<Lesson> lessonList = timeTable.getLessonList();
        Map<Timeslot, Map<Room, List<Lesson>>> lessonMap = lessonList.stream()
                .filter(lesson -> lesson.getTimeslot() != null && lesson.getRoom() != null)
                .collect(Collectors.groupingBy(Lesson::getTimeslot,
                        Collectors.groupingBy(Lesson::getRoom)));
        logger.info("|            | " + roomList.stream()
                .map(room -> String.format("%-10s", room.getName())).collect(Collectors.joining(" | "))
                + " |");
        logger.info("|" + "------------|".repeat(roomList.size() + 1));
        for (Timeslot timeslot : timeTable.getTimeslotList()) {
            List<List<Lesson>> cellList = roomList.stream()
                    .map(room -> {
                        Map<Room, List<Lesson>> byRoomMap = lessonMap.get(timeslot);
                        if (byRoomMap == null) {
                            return Collections.<Lesson>emptyList();
                        }
                        List<Lesson> cellLessonList = byRoomMap.get(room);
                        if (cellLessonList == null) {
                            return Collections.<Lesson>emptyList();
                        }
                        return cellLessonList;
                    })
                    .collect(Collectors.toList());

            logger.info("| " + String.format("%-10s",
                    timeslot.getDayOfWeek().toString().substring(0, 3) + " "
                            + timeslot.getStartTime())
                    + " | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Lesson::getSubject)
                                    .collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            logger.info("|            | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Lesson::getTeacher)
                                    .collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            logger.info("|            | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList.stream().map(Lesson::getStudentGroup)
                                    .collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            logger.info("|" + "------------|".repeat(roomList.size() + 1));
        }
        List<Lesson> unassignedLessons = lessonList.stream()
                .filter(lesson -> lesson.getTimeslot() == null || lesson.getRoom() == null)
                .collect(Collectors.toList());
        if (!unassignedLessons.isEmpty()) {
            logger.info("");
            logger.info("Unassigned lessons");
            for (Lesson lesson : unassignedLessons) {
                logger.info(
                        "  " + lesson.getSubject() + " - " + lesson.getTeacher() + " - "
                                + lesson.getStudentGroup());
            }
        }
        logger.close();
    }
}

interface ILogger {
    void info(String message);

    void close();
}

class Logger implements ILogger {
    public void info(String message) {
        System.out.println(message);
    }

    public void close() {
    }
}

class LoggerFile implements ILogger {
    private File file;
    private FileWriter fileWriter;

    static LoggerFile fromConstants() {
        StringBuilder path = new StringBuilder();
        path.append("reports/");
        path.append(Constants.demoData.toString().toLowerCase());
        path.append("_");
        path.append(Constants.constraintsUsed.toString().toLowerCase());
        path.append("_");
        path.append(Constants.terminationSpentLimit.toString().toLowerCase());
        path.append(".log");
        return new LoggerFile(path.toString());
    }

    public LoggerFile(String path) {
        this.file = new File(path);

        if (this.file.exists()) {
            this.file.delete();
        }

        try {
            this.file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            this.fileWriter = new FileWriter(this.file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void info(String message) {
        try {
            this.fileWriter.write(message + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            this.fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
