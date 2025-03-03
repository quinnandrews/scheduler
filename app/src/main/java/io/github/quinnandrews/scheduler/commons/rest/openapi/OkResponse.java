package io.github.quinnandrews.scheduler.commons.rest.openapi;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(responseCode = "200",
        description = "The operation was successful.")
public @interface OkResponse {

    @AliasFor(annotation = ApiResponse.class)
    Content[] content() default {};
}
