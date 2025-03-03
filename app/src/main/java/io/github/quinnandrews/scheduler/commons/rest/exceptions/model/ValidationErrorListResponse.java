package io.github.quinnandrews.scheduler.commons.rest.exceptions.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = """
        A common Error Response Model specifically for Validation Errors. Returned when a Resource Method \
        fails validation checks. This Model supports common validation scenarios by returning many errors \
        rather than only one and each error details the property that failed and why it failed.""")
public class ValidationErrorListResponse {

    @Schema(example = "validation-errors",
            description = """
                    A human-readable identifier for the kind of errors that occurred. Can be used in all \
                    the ways a numeric error code can be used, but provides meaning on its own and is \
                    likely better at naturally maintaining uniqueness. Can be used as a path or query \
                    parameter in a URL if an API was provided and can also be used in UIs as a key (in \
                    whole or in part) in localization files to provide custom messages for Users.""")
    private String key;

    @Schema(example = "One or more validation errors occurred.",
            description = """
                    A brief message about the validation errors that occurred. May match the message of \
                    the correspondingException, its root cause, or it may be a message customized for \
                    this Response.""")
    private String message;

    @Schema(example = """
                    Validation failed for values in Class com.zoomcare.slots.data.schedules.definition.\
                    ServiceLineListScheduleDefinition when validation checks were triggered in Class \
                    com.zoomcare.slots.data.schedules.repository.ScheduleRepository in Method \
                    getServiceLineScheduleList(). Properties with invalid values are listed below.""",
            description = """
                    A longer description of the validation issues that occurred, the context it occurred in, \
                    advice to assist diagnostics and resolution, etc.""")
    private String description;

    @ArraySchema(schema = @Schema(implementation = ValidationErrorResponse.class),
            arraySchema = @Schema(description = "A List of Validation Errors that occurred."))
    private List<ValidationErrorResponse> errors;

    public ValidationErrorListResponse() {
        // no-op
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<ValidationErrorResponse> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public ValidationErrorListResponse withKey(final String key) {
        this.key = key;
        return this;
    }

    public ValidationErrorListResponse withMessage(final String message) {
        this.message = message;
        return this;
    }

    public ValidationErrorListResponse withDescription(final String description) {
        this.description = description;
        return this;
    }

    public ValidationErrorListResponse withErrors(final List<ValidationErrorResponse> errors) {
        this.errors = errors;
        return this;
    }

    public static class ValidationErrorResponse {

        @Schema(example = "validation-error-notnull",
                description = """
                    A human-readable identifier for the kind of validation error that occurred. Can be \
                    used in all the ways a numeric error code can be used, but provides meaning on its \
                    own and is likely better at naturally maintaining uniqueness. Can be used as a path \
                    or query parameter in a URL if an API was provided and can also be used in UIs as a \
                    key (in whole or in part) in localization files to provide custom messages for Users.""")
        private String key;

        @Schema(example = "date",
                description = """
                    The name of the property that failed validation. There is no guarantee that this name \
                    will match the name of a mapped property in a JSON Request Object and nor is there any \
                    guarantee that the property has an equivalent in a JSON Request Object to begin with.""")
        private String property;

        @Schema(example = "Property 'date' must not be null",
                description = "A brief message about the validation error that occurred.")
        private String message;

        public ValidationErrorResponse() {
            // no-op
        }

        public String getKey() {
            return key;
        }

        public String getProperty() {
            return property;
        }

        public String getMessage() {
            return message;
        }

        public ValidationErrorResponse withKey(final String key) {
            this.key = key;
            return this;
        }

        public ValidationErrorResponse withProperty(final String property) {
            this.property = property;
            return this;
        }

        public ValidationErrorResponse withMessage(final String message) {
            this.message = message;
            return this;
        }
    }
}
