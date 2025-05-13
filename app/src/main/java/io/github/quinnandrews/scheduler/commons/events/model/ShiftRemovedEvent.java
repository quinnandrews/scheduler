package io.github.quinnandrews.scheduler.commons.events.model;

public record ShiftRemovedEvent(
        Long id,
        Integer version,
        EventSummary _eventSummary
) {
}
