package io.github.quinnandrews.scheduler.commons.exceptions;

/**
 * <p> Indicates that access to a given Entity, Resource, etc. is not allowed,
 * either because of permissions or because the state of the Resource at the
 * given time does not allow it. Corresponds to an HTTP Status Code of 403.
 *
 * <p> This Exception is different from a Conflict, which is usually about
 * versioning. It applies to situations where a User tries to perform an
 * operation that cannot be performed under the circumstances, like trying
 * to reserve a Slot that has already been booked, or cancelling an Appointment
 * that is already in progress.
 *
 * <p> This is a common Exception available to every layer in the Application. It
 * provides an optional description property to set contextual information that
 * may have diagnostic value. It is expected to have no Root Cause.
 */
public class ForbiddenException extends RuntimeException {

    private final String description;

    public ForbiddenException(final String message) {
        super(message);
        this.description = null;
    }

    public ForbiddenException(final String message, final String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
