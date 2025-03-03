package io.github.quinnandrews.scheduler.commons.rest.exceptions.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Schema(description = """
        A common Error Response Model to return when a Resource Method fails. The corresponding \
        Exception may or may not be entered in Application logs, depending on the case. For conversion, \
        validation errors, for example, there is no need to log anything.""")
public class ErrorResponse {

    @Schema(example = "internal-server-error",
            description = """
                    A human-readable identifier for the kind of error that occurred. Can be used in all \
                    the ways a numeric error code can be used, but provides meaning on its own and is \
                    likely better at naturally maintaining uniqueness. Can be used as a path or query \
                    parameter in a URL if an API was provided and can also be used in UIs as a key (in \
                    whole or in part) in localization files to provide custom messages for Users.""")
    private String key;

    @Schema(example = "An unexpected error occurred.",
            description = """
                    A brief message about the error that occurred. May match the message of the corresponding \
                    Exception, its root cause, or it may be a message customized for this Response.""")
    private String message;

    @Schema(example = "NullPointerException: Argument 'id' cannot be null.",
            description = """
                    A brief message about the root cause of the error that occurred. If there is no root cause, \
                    this message may match the message of the corresponding Exception or it may not be null.""")
    private String rootCauseMessage;

    @Schema(example = "The Application encountered a condition that prevented it from fulfilling the Request.",
            description = """
                    A longer description of the issue, the context it occurred in, advice to assist diagnostics \
                    and resolution, etc.""")
    private String description;

    @ArraySchema(arraySchema = @Schema(description = """
                    Available only in development environments (not available in QA or Production). Contains \
                    the stacktrace of the corresponding Exception. Each line is an element in the array. Enables \
                    effective development by providing developers with important diagnostic information immediately."""))
    private List<String> stackTrace;

    public ErrorResponse() {
        // no-op
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getRootCauseMessage() {
        return rootCauseMessage;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getStackTrace() {
        if (stackTrace == null) {
            stackTrace = new ArrayList<>();
        }
        return stackTrace.stream()
                .map(l -> l.replace("\t", ""))
                .toList();
    }

    public ErrorResponse withKey(final String key) {
        this.key = key;
        return this;
    }

    public ErrorResponse withMessage(final String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse withRootCauseMessage(final String rootCauseMessage) {
        this.rootCauseMessage = rootCauseMessage;
        return this;
    }

    public ErrorResponse withDescription(final String description) {
        this.description = description;
        return this;
    }

    public ErrorResponse withStackTraceOf(final Throwable throwable) {
        // Only allow Stack Traces in the Response if one of the development profiles is active
//        final var environment = ApplicationContextProvider.getEnvironment();
//        if (environment.acceptsProfiles(Profiles.of("test", "local", "dev"))) {
//            this.stackTrace = Arrays.stream(ExceptionUtils.getStackFrames(throwable)).toList();
//        } else {
//            this.stackTrace = List.of("[see application logs]");
//        }
        this.stackTrace = Arrays.stream(ExceptionUtils.getStackFrames(throwable)).toList();
        return this;
    }
}
