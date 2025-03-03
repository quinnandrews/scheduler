package io.github.quinnandrews.scheduler.commons.exceptions;

/**
 * <p> Indicates that a given Entity, Resource, etc. could not be found. Corresponds
 * to an HTTP Status Code of 404.
 *
 * <p> This is a common Exception available to every layer in the Application. It
 * provides an optional description property to set contextual information that
 * may have diagnostic value. It is expected to have no Root Cause.
 */
public class NotFoundException extends RuntimeException {

    private final String description;

    public NotFoundException(final String message) {
        super(message);
        this.description = null;
    }

    public NotFoundException(final String message, final String description) {
        super(message);
        this.description = description;
    }

    public NotFoundException(final Class<?> type, final Object id) {
        super("Could not find " + type.getSimpleName() +  " with ID[" + id + "].");
        this.description = null;
    }

    public NotFoundException(final Class<?> type, final Object id, final String description) {
        super("Could not find " + type.getSimpleName() + " with ID[" + id + "].");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
