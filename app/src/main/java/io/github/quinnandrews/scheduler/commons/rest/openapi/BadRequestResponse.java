package io.github.quinnandrews.scheduler.commons.rest.openapi;

import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ErrorResponse;
import io.github.quinnandrews.scheduler.commons.rest.exceptions.model.ValidationErrorListResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(responseCode = "400",
        description = "One or more conversion or validation errors occurred.")
public @interface BadRequestResponse {

    @AliasFor(annotation = ApiResponse.class)
    Content[] content() default @Content(schema = @Schema(oneOf = {
            ErrorResponse.class,
            ValidationErrorListResponse.class
    }));
}
