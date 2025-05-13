package io.github.quinnandrews.scheduler.modules.shifts.event.translators;

import io.github.quinnandrews.scheduler.commons.events.model.ShiftRemovedEvent;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;

import static io.github.quinnandrews.scheduler.modules.shifts.event.translators.ShiftEventSummaryTranslator.eventSummaryOf;

public class ShiftRemovedTranslator {

    private ShiftRemovedTranslator() {
        // no-op
    }

    public static ShiftRemovedEvent messageOf(final ShiftEvent event) {
        return new ShiftRemovedEvent(
                event.getShiftId(),
                event.getShiftVersion(),
                eventSummaryOf(event)
        );
    }
}
