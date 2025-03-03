package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.ForbiddenException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> Maps our custom ForbiddenException to our custom Error Response Model
 * with a 403 Status Code.
 */
@Component
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(final ForbiddenException e) {
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("forbidden-error")
                        .withMessage(e.getMessage())
                        .withDescription(getDescription(e)))
                .build();
    }

    private String getDescription(final ForbiddenException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse( """
                The Request is not allowed access to the given Resource. The User may not have the \
                necessary permissions or the Resource may be in a state that bars the User from \
                making the requested action.""");
    }
}