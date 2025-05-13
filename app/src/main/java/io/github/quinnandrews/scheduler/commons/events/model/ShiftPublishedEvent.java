package io.github.quinnandrews.scheduler.commons.events.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShiftPublishedEvent(
        Long id,
        Integer version,
        String status,
        Clinic clinic,
        Employee employee,
        LocalDate localDateStart,
        LocalTime localTimeStart,
        LocalDate localDateEnd,
        LocalTime localTimeEnd,
        EventSummary _eventSummary
) {

    public record Clinic(
            Long id,
            String name,
            String city,
            String state,
            String timeZone
    ) {
    }

    public record Employee(
            Long id,
            String firstName,
            String lastName,
            String credentials
    ) {
    }
}
