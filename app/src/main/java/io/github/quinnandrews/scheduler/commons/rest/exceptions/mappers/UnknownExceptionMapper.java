package io.github.quinnandrews.scheduler.commons.rest.exceptions.mappers;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p> Maps any Exception that has no Mapper defined to our custom Error Response Model
 * with a 500 Status Code.
 *
 * <p> Logs the Exception to Application logs. Provides the message of the root cause
 * as well as a friendly suggestion to implement a custom Mapper if a 500 was not
 * expected.
 */
@Component
@Provider
public class UnknownExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(UnknownExceptionMapper.class);
    
    public Response toResponse(final Throwable e) {
        logger.error("""
                Caught an unknown Exception. Responding with Status Code 500. If 500 was not the intended Status \
                Code, implement a custom ExceptionMapper that responds with the appropriate Code and sets an \
                instance of ErrorResponse in the Response Body that contains relevant information.""", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse()
                        .withKey("unknown-error")
                        .withMessage(e.getMessage())
                        .withRootCauseMessage(ExceptionUtils.getRootCauseMessage(e))
                        .withDescription("""
                                Caught an unknown Exception. See Application logs for details. If 500 was not the \
                                intended Status Code, implement a custom ExceptionMapper that responds with the \
                                appropriate Code and sets an instance of ErrorResponse in the Response Body that \
                                contains relevant information.""")
                        .withStackTraceOf(e))
                .build();
    }
}