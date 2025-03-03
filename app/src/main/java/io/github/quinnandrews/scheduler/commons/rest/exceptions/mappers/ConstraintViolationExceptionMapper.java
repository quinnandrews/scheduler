package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ValidationErrorListResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * <p> Maps Bean Validation's ConstraintViolationException to our custom Validation Error
 * Response Model with a 400 Status Code.
 *
 * <p> Handles ConstraintViolationExceptions thrown during Validation routines. Details where
 * validation errors occurred and what method triggered validation checks. Includes a
 * List of ValidationErrorResponses that detail each validation error.
 *
 * <p> Developers implementing Validation with Annotations do not need to implement custom messages
 * to include the name of the property that failed. This Mapper will join the property name
 * together with the default messages to produce friendly messages in the Response.
 */
@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String UNKNOWN = "[unknown]";

    public Response toResponse(final ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ValidationErrorListResponse()
                        .withKey("validation-errors")
                        .withMessage("One or more validation errors occurred.")
                        .withDescription("Validation failed for values in Class "
                                + getValueContainerClass(e) + " when validation " +
                                "checks were triggered in Class " + getValidatingClass(e) +
                                " in Method " + getValidatingMethod(e) + "(). Properties " +
                                "with invalid values are listed below.")
                        .withErrors(buildErrors(e)))
                .build();
    }

    private String getValueContainerClass(final ConstraintViolationException e) {
        final Optional<ConstraintViolation<?>> optional = e.getConstraintViolations().stream().findFirst();
        return optional.map(cv -> cv.getLeafBean().getClass().getName()).orElse(UNKNOWN);
    }

    private String getValidatingClass(final ConstraintViolationException e) {
        final Optional<ConstraintViolation<?>> optional = e.getConstraintViolations().stream().findFirst();
        return optional.map(cv -> cv.getRootBean().getClass().getName()).orElse(UNKNOWN);
    }

    private String getValidatingMethod(final ConstraintViolationException e) {
        final Optional<ConstraintViolation<?>> optional = e.getConstraintViolations().stream().findFirst();
        final Path.Node node = optional.flatMap(cv ->
                StreamSupport.stream(cv.getPropertyPath().spliterator(), Boolean.FALSE).findFirst()
        ).orElse(null);
        return node != null ? node.getName() : UNKNOWN;
    }

    private List<ValidationErrorListResponse.ValidationErrorResponse> buildErrors(final ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(cv -> {
                    final String property = getErrorProperty(cv.getPropertyPath());
                    return new ValidationErrorListResponse.ValidationErrorResponse()
                            .withKey(getErrorKey(cv))
                            .withProperty(property)
                            .withMessage("Property '" + property + "' " + cv.getMessage());
                })
                .toList();
    }

    private String getErrorKey(final ConstraintViolation<?> constraintViolation) {
        return "validation-error-" + constraintViolation.getConstraintDescriptor()
                .getAnnotation().annotationType().getSimpleName().toLowerCase();
    }

    private String getErrorProperty(final Path path) {
        final Path.Node node = StreamSupport.stream(path.spliterator(), Boolean.FALSE)
                .reduce((first, second) -> second)
                .orElse(null);
        return node != null ? node.getName() : UNKNOWN;
    }
}