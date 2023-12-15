package com.ipb.domain;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @PlanningId
    private Long id;

    private String name;

    // No-arg constructor required for Hibernate
    public Room(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}