package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

/**
 * <p> Maps JAX-RS NotSupportedException to our custom Error Response Model
 * with a 415 Status Code.
 */
@Component
@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {

    @Override
    public Response toResponse(final NotSupportedException e) {
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("media-type-not-supported-error")
                        .withMessage("The Requested Media Type is not supported by this API.")
                        .withDescription("See API Documentation for supported Media Types."))
                .build();
    }
}