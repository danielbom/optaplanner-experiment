package com.ipb.demo;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.ipb.domain.Lesson;
import com.ipb.domain.Room;
import com.ipb.domain.Timeslot;
import com.ipb.persistence.LessonRepository;
import com.ipb.persistence.RoomRepository;
import com.ipb.persistence.TimeslotRepository;

public class DemoDataGenerator {
    DemoData demoData;

    TimeslotRepository timeslotRepository;
    RoomRepository roomRepository;
    LessonRepository lessonRepository;

    public DemoDataGenerator(DemoData demoData, TimeslotRepository timeslotRepository, RoomRepository roomRepository,
            LessonRepository lessonRepository) {
        this.demoData = demoData;
        this.timeslotRepository = timeslotRepository;
        this.roomRepository = roomRepository;
        this.lessonRepository = lessonRepository;
    }

    public void generate() {
        if (demoData == DemoData.NONE) {
            return;
        }

        List<Timeslot> timeslotList = new ArrayList<>(10);
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        if (demoData == DemoData.LARGE) {
            timeslotList.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslotList.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        }
        timeslotRepository.persist(timeslotList);

        List<Room> roomList = new ArrayList<>(3);
        roomList.add(new Room("Room A"));
        roomList.add(new Room("Room B"));
        roomList.add(new Room("Room C"));
        if (demoData == DemoData.LARGE) {
            roomList.add(new Room("Room D"));
            roomList.add(new Room("Room E"));
            roomList.add(new Room("Room F"));
        }
        roomRepository.persist(roomList);

        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson("Math", "A. Turing", "9th grade"));
        lessonList.add(new Lesson("Math", "A. Turing", "9th grade"));
        lessonList.add(new Lesson("Physics", "M. Curie", "9th grade"));
        lessonList.add(new Lesson("Chemistry", "M. Curie", "9th grade"));
        lessonList.add(new Lesson("Biology", "C. Darwin", "9th grade"));
        lessonList.add(new Lesson("History", "I. Jones", "9th grade"));
        lessonList.add(new Lesson("English", "I. Jones", "9th grade"));
        lessonList.add(new Lesson("English", "I. Jones", "9th grade"));
        lessonList.add(new Lesson("Spanish", "P. Cruz", "9th grade"));
        lessonList.add(new Lesson("Spanish", "P. Cruz", "9th grade"));
        if (demoData == DemoData.LARGE) {
            lessonList.add(new Lesson("Math", "A. Turing", "9th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "9th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "9th grade"));
            lessonList.add(new Lesson("ICT", "A. Turing", "9th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "9th grade"));
            lessonList.add(new Lesson("Geography", "C. Darwin", "9th grade"));
            lessonList.add(new Lesson("Geology", "C. Darwin", "9th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "9th grade"));
            lessonList.add(new Lesson("English", "I. Jones", "9th grade"));
            lessonList.add(new Lesson("Drama", "I. Jones", "9th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "9th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "9th grade"));
            lessonList.add(new Lesson("P.E.", "C. Lewis", "9th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "9th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "9th grade")); // Physical Education
        }

        lessonList.add(new Lesson("Math", "A. Turing", "10th grade"));
        lessonList.add(new Lesson("Math", "A. Turing", "10th grade"));
        lessonList.add(new Lesson("Math", "A. Turing", "10th grade"));
        lessonList.add(new Lesson("Physics", "M. Curie", "10th grade"));
        lessonList.add(new Lesson("Chemistry", "M. Curie", "10th grade"));
        lessonList.add(new Lesson("French", "M. Curie", "10th grade"));
        lessonList.add(new Lesson("Geography", "C. Darwin", "10th grade"));
        lessonList.add(new Lesson("History", "I. Jones", "10th grade"));
        lessonList.add(new Lesson("English", "P. Cruz", "10th grade"));
        lessonList.add(new Lesson("Spanish", "P. Cruz", "10th grade"));
        if (demoData == DemoData.LARGE) {
            lessonList.add(new Lesson("Math", "A. Turing", "10th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "10th grade"));
            lessonList.add(new Lesson("ICT", "A. Turing", "10th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "10th grade"));
            lessonList.add(new Lesson("Biology", "C. Darwin", "10th grade"));
            lessonList.add(new Lesson("Geology", "C. Darwin", "10th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "10th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "10th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "10th grade"));
            lessonList.add(new Lesson("Drama", "I. Jones", "10th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "10th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "10th grade"));
            lessonList.add(new Lesson("P.E.", "C. Lewis", "10th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "10th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "10th grade")); // Physical Education

            lessonList.add(new Lesson("Math", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("ICT", "A. Turing", "11th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "11th grade"));
            lessonList.add(new Lesson("Chemistry", "M. Curie", "11th grade"));
            lessonList.add(new Lesson("French", "M. Curie", "11th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "11th grade"));
            lessonList.add(new Lesson("Geography", "C. Darwin", "11th grade"));
            lessonList.add(new Lesson("Biology", "C. Darwin", "11th grade"));
            lessonList.add(new Lesson("Geology", "C. Darwin", "11th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "11th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "11th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "11th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "11th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "11th grade"));
            lessonList.add(new Lesson("Spanish", "P. Cruz", "11th grade"));
            lessonList.add(new Lesson("Drama", "P. Cruz", "11th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "11th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "11th grade"));
            lessonList.add(new Lesson("P.E.", "C. Lewis", "11th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "11th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "11th grade")); // Physical Education

            lessonList.add(new Lesson("Math", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("Math", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("ICT", "A. Turing", "12th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "12th grade"));
            lessonList.add(new Lesson("Chemistry", "M. Curie", "12th grade"));
            lessonList.add(new Lesson("French", "M. Curie", "12th grade"));
            lessonList.add(new Lesson("Physics", "M. Curie", "12th grade"));
            lessonList.add(new Lesson("Geography", "C. Darwin", "12th grade"));
            lessonList.add(new Lesson("Biology", "C. Darwin", "12th grade"));
            lessonList.add(new Lesson("Geology", "C. Darwin", "12th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "12th grade"));
            lessonList.add(new Lesson("History", "I. Jones", "12th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "12th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "12th grade"));
            lessonList.add(new Lesson("English", "P. Cruz", "12th grade"));
            lessonList.add(new Lesson("Spanish", "P. Cruz", "12th grade"));
            lessonList.add(new Lesson("Drama", "P. Cruz", "12th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "12th grade"));
            lessonList.add(new Lesson("Art", "S. Dali", "12th grade"));
            lessonList.add(new Lesson("P.E.", "C. Lewis", "12th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "12th grade")); // Physical Education
            lessonList.add(new Lesson("P.E.", "C. Lewis", "12th grade")); // Physical Education
        }

        Lesson lesson = lessonList.get(0);
        lesson.setTimeslot(timeslotList.get(0));
        lesson.setRoom(roomList.get(0));

        lessonRepository.persist(lessonList);
    }

    public enum DemoData {
        NONE,
        SMALL,
        LARGE
    }
}
