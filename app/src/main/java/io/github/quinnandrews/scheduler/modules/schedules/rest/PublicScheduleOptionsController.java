package io.github.quinnandrews.scheduler.modules.schedules.rest;

import io.github.quinnandrews.scheduler.commons.rest.annotations.PublicAPI;
import io.github.quinnandrews.scheduler.commons.rest.openapi.BadRequestResponse;
import io.github.quinnandrews.scheduler.commons.rest.openapi.InternalServerErrorResponse;
import io.github.quinnandrews.scheduler.commons.rest.openapi.NotFoundResponse;
import io.github.quinnandrews.scheduler.commons.rest.openapi.OkResponse;
import io.github.quinnandrews.scheduler.config.rest.OpenApiDocumentConfig;
import io.github.quinnandrews.scheduler.modules.schedules.core.PublicScheduleOptionsService;
import io.github.quinnandrews.scheduler.modules.schedules.rest.model.LocationOptionListResponse;
import io.github.quinnandrews.scheduler.modules.schedules.rest.model.LocationOptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;

@PublicAPI
@Path("/public/booking-api/options")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = OpenApiDocumentConfig.PUBLIC_LOCATION_TAG)
@Controller
public class PublicScheduleOptionsController {

    private final PublicScheduleOptionsService scheduleOptionsService;

    public PublicScheduleOptionsController(final PublicScheduleOptionsService scheduleOptionsService) {
        this.scheduleOptionsService = scheduleOptionsService;
    }

    @GET
    @Path("/locations/{locationId}")
    @Operation(description = """
            Gets an existing, active Location. Requests for an inactive Location \
            will return a Not Found error (404).""")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationOptionResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @InternalServerErrorResponse
    public LocationOptionResponse getLocationOption(@PathParam("locationId") final Integer id) {
        return LocationOptionResponse.of(scheduleOptionsService.getLocationOption(id));
    }

    @GET
    @Path("/locations")
    @Operation(description = """
            Gets a list of all active Locations. Inactive Locations are excluded.""")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationOptionListResponse.class)))
    @InternalServerErrorResponse
    public LocationOptionListResponse getAllLocationOptions() {
        return LocationOptionListResponse.of(scheduleOptionsService.getAllLocationOptions());
    }
}
