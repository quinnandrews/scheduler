package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.NotFoundException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> Maps our custom NotFoundException to our custom Error Response Model
 * with a 404 Status Code.
 */
@Component
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(final NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("not-found-error")
                        .withMessage(e.getMessage())
                        .withDescription(getDescription(e)))
                .build();
    }

    private String getDescription(final NotFoundException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse("The Requested resource could not be found.");
    }
}