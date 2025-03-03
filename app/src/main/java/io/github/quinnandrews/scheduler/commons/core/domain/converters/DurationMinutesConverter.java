package io.github.quinnandrews.scheduler.commons.core.domain.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter(autoApply = false)
public class DurationMinutesConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(final Duration attribute) {
        return attribute.toMinutes();
    }
 
    @Override
    public Duration convertToEntityAttribute(final Long duration) {
        return Duration.ofMinutes(duration);
    }
}