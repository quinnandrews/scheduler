package io.github.quinnandrews.scheduler.commons.events.model;

import java.time.Instant;

public record EventSummary(
        Long eventId,
        String eventEntity,
        String eventType,
        Instant dateEventOccurred
) {
}
