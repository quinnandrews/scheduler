package io.github.quinnandrews.scheduler.commons.exceptions;

/**
 * <p> Indicates that a given Entity, Resource, etc. has been removed and it is
 * likely permanent. Corresponds to an HTTP Status Code of 410.
 *
 * <p> If prior existence is unknown, or it is not known whether the absence is
 * permanent or temporary, then a NotFoundException should be thrown instead.
 *
 * <p> This is a common Exception available to every layer in the Application. It
 * provides an optional description property to set contextual information that
 * may have diagnostic value. It is expected to have no Root Cause.
 */
public class GoneException extends RuntimeException {

    private final String description;

    public GoneException(final String message) {
        super(message);
        this.description = null;
    }

    public GoneException(final String message, final String description) {
        super(message);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
