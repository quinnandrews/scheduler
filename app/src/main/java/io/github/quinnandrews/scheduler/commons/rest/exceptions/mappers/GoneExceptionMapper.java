package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.GoneException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> Maps our custom GoneException to our custom Error Response Model
 * with a 410 Status Code.
 */
@Component
@Provider
public class GoneExceptionMapper implements ExceptionMapper<GoneException> {

    @Override
    public Response toResponse(final GoneException e) {
        return Response.status(Response.Status.GONE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("gone-error")
                        .withMessage(e.getMessage())
                        .withDescription(getDescription(e)))
                .build();
    }

    private String getDescription(final GoneException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse("The Requested resource has been removed.");
    }
}