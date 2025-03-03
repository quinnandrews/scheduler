package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.InternalServerException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> Maps our custom InternalServerException to our custom Error Response Model
 * with a 500 Status Code.
 */
@Component
@Provider
public class InternalServerExceptionMapper implements ExceptionMapper<InternalServerException> {

    @Override
    public Response toResponse(final InternalServerException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("internal-server-error")
                        .withMessage(e.getMessage())
                        .withRootCauseMessage(ExceptionUtils.getRootCauseMessage(e))
                        .withDescription(getDescription(e))
                        .withStackTraceOf(e))
                .build();
    }

    private String getDescription(final InternalServerException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse("The Application encountered a condition that prevented it from fulfilling the Request.");
    }
}