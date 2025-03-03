package io.github.quinnandrews.scheduler.commons.exceptions;

/**
 * <p> Indicates that the Request is in conflict with the current state of the given
 * Resource. Corresponds to an HTTP Status Code of 409.
 *
 * <p> Useful in cases when Optimistic Locking is enabled for a given Entity, to block
 * Users from unintentionally updating an Entity with an obsolete version that may
 * overwrite changes already persisted.
 *
 * <p> A common Exception available to every layer in the Application. It provides an
 * optional description property to set contextual information that may have diagnostic
 * value.
 */
public class ConflictException extends RuntimeException {

    private final String description;

    public ConflictException(final String message) {
        super(message);
        this.description = null;
    }

    public ConflictException(final String message, final String description) {
        super(message);
        this.description = description;
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
        this.description = null;
    }

    public ConflictException(final String message, final Throwable cause, final String description) {
        super(message, cause);
        this.description = description;
    }

    public ConflictException(final Throwable cause) {
        super(cause);
        this.description = null;
    }

    public ConflictException(final Throwable cause, final String description) {
        super(cause);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
