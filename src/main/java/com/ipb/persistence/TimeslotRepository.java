package com.ipb.persistence;

import java.util.ArrayList;
import java.util.List;

import com.ipb.domain.Timeslot;

public class TimeslotRepository {
    private long id = 0;
    private List<Timeslot> entities = new ArrayList<Timeslot>();

    public void persist(List<Timeslot> timeslotList) {
        timeslotList.stream().forEach(newEntity -> {
            newEntity.setId(id);
            entities.add(newEntity);
            id++;
        });
    }

    public List<Timeslot> getAll() {
        return entities;
    }
}
