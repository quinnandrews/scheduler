package io.github.quinnandrews.scheduler.modules.shifts.event.translators;

import io.github.quinnandrews.scheduler.commons.events.model.ShiftPublishedEvent;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Clinic;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Employee;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;

import static io.github.quinnandrews.scheduler.modules.shifts.event.translators.ShiftEventSummaryTranslator.eventSummaryOf;

public class ShiftPublishedTranslator {

    private ShiftPublishedTranslator() {
        // no-op
    }

    public static ShiftPublishedEvent messageOf(final Shift shift,
                                                final ShiftEvent event) {
        return new ShiftPublishedEvent(
                shift.getId(),
                shift.getVersion(),
                shift.getStatusCode().toString(),
                clinicOf(shift.getClinic()),
                employeeOf(shift.getEmployee()),
                shift.getLocalDateStart(),
                shift.getLocalTimeStart(),
                shift.getLocalDateEnd(),
                shift.getLocalTimeEnd(),
                eventSummaryOf(event)
        );
    }

    private static ShiftPublishedEvent.Clinic clinicOf(final Clinic clinic) {
        return new ShiftPublishedEvent.Clinic(
                clinic.getId(),
                clinic.getName(),
                clinic.getCity(),
                clinic.getState(),
                clinic.getTimeZone()
        );
    }

    private static ShiftPublishedEvent.Employee employeeOf(final Employee employee) {
        return new ShiftPublishedEvent.Employee(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getCredentials()
        );
    }
}
