package io.github.quinnandrews.scheduler.modules.shifts.event.translators;

import io.github.quinnandrews.scheduler.commons.events.model.EventSummary;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;

public class ShiftEventSummaryTranslator {

    private ShiftEventSummaryTranslator() {
        // no-op
    }

    public static EventSummary eventSummaryOf(final ShiftEvent event) {
        return new EventSummary(
                event.getId(),
                Shift.class.getSimpleName(),
                event.getTypeCode().toString(),
                event.getDateOccurred()
        );
    }
}
