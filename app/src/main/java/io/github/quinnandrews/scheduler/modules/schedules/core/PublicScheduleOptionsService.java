package io.github.quinnandrews.scheduler.modules.schedules.core;

import io.github.quinnandrews.scheduler.commons.exceptions.NotFoundException;
import io.github.quinnandrews.scheduler.modules.schedules.core.domain.LocationOption;
import io.github.quinnandrews.scheduler.modules.schedules.core.domain.LocationOption_;
import io.github.quinnandrews.scheduler.modules.schedules.core.domain.repositories.LocationOptionRepository;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
@Service
public class PublicScheduleOptionsService {

    private final LocationOptionRepository locationOptionRepository;

    public PublicScheduleOptionsService(final LocationOptionRepository locationOptionRepository) {
        this.locationOptionRepository = locationOptionRepository;
    }

    @Transactional(readOnly = true)
    public LocationOption getLocationOption(final Integer id) {
        Objects.requireNonNull(id, "Argument 'id' cannot be null.");
        return locationOptionRepository.findOneWithCaching(
                SpecificationBuilder.from(LocationOption.class)
                        .where().isEqualTo(LocationOption_.id, id)
                        .and().isEqualTo(LocationOption_.status, LocationOption.Status.ACTIVE)
                        .toSpecification()
        ).orElseThrow(() -> new NotFoundException(LocationOption.class, id));
    }

    @Transactional(readOnly = true)
    public List<LocationOption> getAllLocationOptions() {
        return locationOptionRepository.findAllWithCaching(
                SpecificationBuilder.from(LocationOption.class)
                        .where().isEqualTo(LocationOption_.status, LocationOption.Status.ACTIVE)
                        .toSpecification(),
                Sort.by(LocationOption_.STATE, LocationOption_.NAME)
        );
    }
}
