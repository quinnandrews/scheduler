package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.ConflictException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * <p> Maps our custom ConflictException to our custom Error Response Model
 * with a 409 Status Code.
 */
@Component
@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(final ConflictException e) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("conflict-error")
                        .withMessage(e.getMessage())
                        .withRootCauseMessage(ExceptionUtils.getRootCauseMessage(e))
                        .withDescription(getDescription(e))
                        .withStackTraceOf(e))
                .build();
    }

    private String getDescription(final ConflictException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse("""
                The Request is in conflict with the current state of the given Resource. This is \
                most likely because the version of the Resource given with the Request is outdated.""");
    }
}