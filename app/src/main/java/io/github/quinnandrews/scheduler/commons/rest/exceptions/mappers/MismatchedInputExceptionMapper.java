package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

/**
 * <p> Maps Jackson's MismatchedInputException to our custom Error Response Model
 * with a 400 Status Code.
 *
 * <p> Handles conversion errors during JSON -> Java Deserialization of Request Objects. Default
 * handling provided by the framework includes invalid values in a text Response and does not
 * sanitize them fist. This implementation excludes invalid values for security reasons and
 * outputs a JSON Response.
 */
@Component
@Provider
public class MismatchedInputExceptionMapper implements ExceptionMapper<MismatchedInputException> {

    @Override
    public Response toResponse(final MismatchedInputException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("conversion-error")
                        .withMessage("Property '" + e.getPath().get(0).getFieldName() +
                                "' was set with an incompatible type. Should convert to " +
                                e.getTargetType().getName() + ".")
                        .withDescription("""
                                Conversion from JSON Request Body has failed. Check input parameters for \
                                inconsistencies. Incompatible values are excluded from this Response for \
                                security reasons."""))
                .build();
    }
}