package io.github.quinnandrews.scheduler.modules.administration.schedules.rest;

import io.github.quinnandrews.scheduler.commons.rest.annotations.StaffAPI;
import io.github.quinnandrews.scheduler.commons.rest.openapi.*;
import io.github.quinnandrews.scheduler.commons.snapshots.rest.model.SnapshotHistoryResponse;
import io.github.quinnandrews.scheduler.config.rest.OpenApiDocumentConfig;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.LocationService;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;
import io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model.LocationCreateRequest;
import io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model.LocationListResponse;
import io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model.LocationResponse;
import io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model.LocationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;

@StaffAPI
@Path("/restricted/administration-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = OpenApiDocumentConfig.RESTRICTED_LOCATION_ADMIN_TAG)
@Controller
public class LocationController {

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @POST
    @Path("/locations")
    @Operation(description = "Creates a new Location.")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public LocationResponse createLocation(final LocationCreateRequest request) {
        return LocationResponse.of(locationService.createLocation(request.toLocation()));
    }

    @PUT
    @Path("/locations/{id}")
    @Operation(description = "Updates an existing Location.")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public LocationResponse updateLocation(@PathParam("id") final Long id,
                                           final LocationUpdateRequest request) {
        return LocationResponse.of(locationService.updateLocation(request.toLocation(id)));
    }

    @GET
    @Path("/locations/{id}")
    @Operation(description = "Gets an existing Location.")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public LocationResponse getLocation(@PathParam("id") Long id) {
        return LocationResponse.of(locationService.getLocationOrElseThrow(id));
    }

    @GET
    @Path("/locations/{id}/history")
    @Operation(description = "Gets the history of an existing Location.")
    @OkResponse(content = @Content(schema = @Schema(implementation = SnapshotHistoryResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public SnapshotHistoryResponse<Location, Long> getLocationHistory(@PathParam("id") final Long id) {
        return SnapshotHistoryResponse.of(locationService.getLocationHistoryOrElseThrow(id));
    }

    @GET
    @Path("/locations")
    @Operation(description = "Gets a list of all existing Locations.")
    @OkResponse(content = @Content(schema = @Schema(implementation = LocationListResponse.class)))
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public LocationListResponse getAllLocations() {
        return LocationListResponse.of(locationService.getAllLocations());
    }
}
