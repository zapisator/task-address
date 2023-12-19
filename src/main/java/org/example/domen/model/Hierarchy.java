package org.example.domen.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Hierarchy(
        int id,
        int objectId,
        int parentObjId,
        int changeId,
        int prevId,
        int nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive
) {
}
