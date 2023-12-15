package com.ipb.persistence;

import java.util.ArrayList;
import java.util.List;

import com.ipb.domain.Room;

public class RoomRepository {
    private long id = 0;
    private List<Room> entities = new ArrayList<Room>();

    public void persist(List<Room> roomList) {
        roomList.stream().forEach(newEntity -> {
            newEntity.setId(id);
            entities.add(newEntity);
            id++;
        });
    }

    public List<Room> getAll() {
        return entities;
    }
}
