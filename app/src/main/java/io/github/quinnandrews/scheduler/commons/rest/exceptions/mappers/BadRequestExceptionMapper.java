package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.exceptions.BadRequestException;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * <p> Maps our custom BadRequestException to our custom Error Response Model
 * with a 400 Status Code.
 */
@Component
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(final BadRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("bad-request-error")
                        .withMessage(e.getMessage())
                        .withDescription(getDescription(e)))
                .build();
    }

    private String getDescription(final BadRequestException e) {
        return Optional.ofNullable(e.getDescription())
                .orElse("""
                The Request was malformed. This is usually because of an invalid \
                input parameter.""");
    }
}