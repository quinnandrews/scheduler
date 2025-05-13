package io.github.quinnandrews.scheduler.modules.shifts.rest;

import io.github.quinnandrews.scheduler.commons.rest.annotations.StaffAPI;
import io.github.quinnandrews.scheduler.commons.rest.openapi.*;
import io.github.quinnandrews.scheduler.modules.shifts.core.ShiftService;
import io.github.quinnandrews.scheduler.modules.shifts.rest.model.ShiftCreateRequest;
import io.github.quinnandrews.scheduler.modules.shifts.rest.model.ShiftResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@StaffAPI
@Path("/staff/shift-management-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Tag(name = OpenApiDocumentConfig.RESTRICTED_LOCATION_ADMIN_TAG)
@Controller
public class ShiftController {

    private static final Logger logger = LoggerFactory.getLogger(ShiftController.class);

    private final ShiftService shiftService;

    public ShiftController(final ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @POST
    @Path("/shifts")
    @Operation(description = "Creates a new Shift.")
    @OkResponse(content = @Content(schema = @Schema(implementation = ShiftResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public ShiftResponse createShift(final ShiftCreateRequest request) {
        return ShiftResponse.of(shiftService.createShift(request.toShift()));
    }

    @POST
    @Path("/shifts/{id}/publish")
    @Operation(description = "Publishes an existing Shift.")
    @OkResponse(content = @Content(schema = @Schema(implementation = ShiftResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public ShiftResponse publishShift(@PathParam("id") final Long id) {
        return ShiftResponse.of(shiftService.publishShift(id));
    }

    @POST
    @Path("/shifts/{id}/remove")
    @Operation(description = "Removes an existing Shift.")
    @OkResponse(content = @Content(schema = @Schema(implementation = ShiftResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public ShiftResponse removeShift(@PathParam("id") final Long id) {
        return ShiftResponse.of(shiftService.removeShift(id));
    }

    @GET
    @Path("/shifts/{id}")
    @Operation(description = "Gets an existing Shift.")
    @OkResponse(content = @Content(schema = @Schema(implementation = ShiftResponse.class)))
    @BadRequestResponse
    @NotFoundResponse
    @UnauthorizedResponse
    @ForbiddenResponse
    @InternalServerErrorResponse
    public ShiftResponse getShift(@PathParam("id") final Long id) {
        return ShiftResponse.of(shiftService.getShiftOrElseThrow(id));
    }
}
