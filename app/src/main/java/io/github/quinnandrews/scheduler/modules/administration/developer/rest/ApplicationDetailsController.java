package io.github.quinnandrews.scheduler.modules.administration.developer.rest;

import io.github.quinnandrews.scheduler.modules.administration.developer.core.ApplicationDetailsService;
import io.github.quinnandrews.scheduler.modules.administration.developer.rest.model.ApplicationDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;

@Controller
@Path("/developer/application")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationDetailsController {

    private final ApplicationDetailsService applicationDetailsService;

    public ApplicationDetailsController(final ApplicationDetailsService applicationDetailsService) {
        this.applicationDetailsService = applicationDetailsService;
    }

    @GET
    @Path("/details")
    @Operation(hidden = true)
    public ApplicationDetailsResponse getApplicationDetails() {
        return ApplicationDetailsResponse.from(applicationDetailsService.getApplicationDetails());
    }
}
