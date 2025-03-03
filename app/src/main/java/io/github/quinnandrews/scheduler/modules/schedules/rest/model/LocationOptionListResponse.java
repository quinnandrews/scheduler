package io.github.quinnandrews.scheduler.modules.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.schedules.core.domain.LocationOption;

import java.util.List;

public record LocationOptionListResponse(List<LocationOptionResponse> locations) {

    public static LocationOptionListResponse of(final List<LocationOption> locations) {
        return new LocationOptionListResponse(
                locations.stream()
                        .map(LocationOptionResponse::of)
                        .toList()
        );
    }
}
