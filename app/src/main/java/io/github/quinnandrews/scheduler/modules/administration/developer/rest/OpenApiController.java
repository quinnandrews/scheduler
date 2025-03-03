package io.github.quinnandrews.scheduler.modules.administration.developer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import io.github.quinnandrews.scheduler.commons.exceptions.InternalServerException;
import io.github.quinnandrews.scheduler.commons.exceptions.NotFoundException;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@Path("/developer/docs/api/rest")
@Produces(MediaType.APPLICATION_JSON)
public class OpenApiController {

    private final Application jaxRsApplication;
    private final SwaggerConfiguration publicOpenApiConfig;
    private final SwaggerConfiguration restrictedOpenApiConfig;

    public OpenApiController(final Application jaxRsApplication,
                             @Qualifier("publicOpenApiConfig")
                             final SwaggerConfiguration publicOpenApiConfig,
                             @Qualifier("restrictedOpenApiConfig")
                             final SwaggerConfiguration restrictedOpenApiConfig) {
        this.jaxRsApplication = jaxRsApplication;
        this.publicOpenApiConfig = publicOpenApiConfig;
        this.restrictedOpenApiConfig = restrictedOpenApiConfig;
    }

    @GET
    @Path("/public/openapi.json")
    @Operation(hidden = true)
    public Response getPublicOpenApiDocument() {
        return generateOpenApiDocument(publicOpenApiConfig);
    }

    @GET
    @Path("/restricted/openapi.json")
    @Operation(hidden = true)
    public Response getRestrictedOpenApiDocument() {
        return generateOpenApiDocument(restrictedOpenApiConfig);
    }

    private Response generateOpenApiDocument(final SwaggerConfiguration config) {
        try {
            final var openApiContext = new JaxrsOpenApiContextBuilder<>()
                    .resourceClasses(config.getResourceClasses())
                    .application(jaxRsApplication)
                    .openApiConfiguration(config)
                    .ctxId(config.getId())
                    .buildContext(Boolean.TRUE);
            final var openAPI = Optional.ofNullable(openApiContext.read())
                    .orElseThrow(() ->
                            new NotFoundException("Could not find Open API Document with ID[" + config.getId() + "]."));
            final var body = openApiContext.getOutputJsonMapper().writer(new DefaultPrettyPrinter())
                    .writeValueAsString(openAPI);
            return Response.status(Response.Status.OK)
                    .entity(body)
                    .build();
        } catch (final OpenApiConfigurationException | JsonProcessingException e) {
            throw new InternalServerException(e);
        }
    }
}
