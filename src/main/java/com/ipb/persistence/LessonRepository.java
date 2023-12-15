package com.ipb.persistence;

import java.util.ArrayList;
import java.util.List;

import com.ipb.domain.Lesson;

public class LessonRepository {
    private long id = 0;
    private List<Lesson> entities = new ArrayList<Lesson>();

    public void persist(List<Lesson> lessonList) {
        lessonList.stream().forEach(newEntity -> {
            newEntity.setId(id);
            entities.add(newEntity);
            id++;
        });
    }

    public List<Lesson> getAll() {
        return entities;
    }
}
