package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

/**
 * <p> Maps JAX-RS NotAllowedException to our custom Error Response Model
 * with a 405 Status Code.
 */
@Component
@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(final NotAllowedException e) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("method-not-allowed-error")
                        .withMessage("The Requested Method is not allowed in this API.")
                        .withDescription("See API Documentation for supported Methods."))
                .build();
    }
}