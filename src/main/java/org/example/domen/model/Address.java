package org.example.domen.model;

import java.time.LocalDate;
import java.util.UUID;

public record Address(
        int id,
        int objectId,
        UUID objectGuid,
        int changeId,
        String name,
        String typename,
        int level,
        int openTypeId,
        int prevId,
        int nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive
) {
}
