package io.github.quinnandrews.scheduler.commons.exceptions;

/**
 * <p> Indicates that the Application encountered an unexpected condition that prevented
 * it from fulfilling the Request. Corresponds to an HTTP Status Code of 500.
 *
 * <p> A common Exception available to every layer in the Application. It provides an
 * optional description property to set contextual information that may have diagnostic
 * value.
 */
public class InternalServerException extends RuntimeException {

    private final String description;

    public InternalServerException(final String message) {
        super(message);
        this.description = null;
    }

    public InternalServerException(final String message, final String description) {
        super(message);
        this.description = description;
    }

    public InternalServerException(final String message, final Throwable cause) {
        super(message, cause);
        this.description = null;
    }

    public InternalServerException(final String message, final Throwable cause, final String description) {
        super(message, cause);
        this.description = description;
    }

    public InternalServerException(final Throwable cause) {
        super(cause);
        this.description = null;
    }

    public InternalServerException(final Throwable cause, final String description) {
        super(cause);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
