package io.github.quinnandrews.scheduler.commons.rest.openapi;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(responseCode = "500",
        description = "An unexpected error occurred.")
public @interface InternalServerErrorResponse {

    @AliasFor(annotation = ApiResponse.class)
    Content[] content() default @Content(schema = @Schema(implementation = ErrorResponse.class));
}
