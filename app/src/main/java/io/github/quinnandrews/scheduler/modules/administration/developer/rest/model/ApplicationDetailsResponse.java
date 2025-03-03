package io.github.quinnandrews.scheduler.modules.administration.developer.rest.model;

import io.github.quinnandrews.scheduler.modules.administration.developer.core.domain.ApplicationDetails;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

public record ApplicationDetailsResponse(String environment,
                                         Integer port,
                                         String contextPath,
                                         String name,
                                         String description,
                                         String group,
                                         String artifactId,
                                         String version,
                                         ZonedDateTime dateBuilt,
                                         Duration timeSinceBuild,
                                         String timeSinceBuildText,
                                         ZonedDateTime dateStarted,
                                         Duration timeSinceStart,
                                         String timeSinceStartText,
                                         String javaVersion,
                                         String springVersion,
                                         String springBootVersion,
                                         String activeProfiles,
                                         List<Metric> metrics) {

    public static ApplicationDetailsResponse from(final ApplicationDetails applicationDetails) {
        final var now = Instant.now();
        final var timeSinceBuild = Duration.between(applicationDetails.instantBuilt(), now);
        final var timeSinceBuildText = getDurationText(timeSinceBuild);
        final var timeSinceStart = Duration.between(applicationDetails.instantStarted(), now);
        final var timeSinceStartText = getDurationText(timeSinceStart);
        return new ApplicationDetailsResponse(
                applicationDetails.environment(),
                applicationDetails.port(),
                applicationDetails.contextPath(),
                applicationDetails.name(),
                applicationDetails.description(),
                applicationDetails.group(),
                applicationDetails.artifactId(),
                applicationDetails.version(),
                getDate(applicationDetails.instantBuilt()),
                timeSinceBuild,
                timeSinceBuildText,
                getDate(applicationDetails.instantStarted()),
                timeSinceStart,
                timeSinceStartText,
                applicationDetails.javaVersion(),
                applicationDetails.springVersion(),
                applicationDetails.springBootVersion(),
                applicationDetails.activeProfiles(),
                applicationDetails.metrics().entrySet().stream()
                        .map(es -> new Metric(es.getKey(), es.getValue()))
                        .sorted(Comparator.comparing(Metric::name))
                        .toList());
    }

    private static ZonedDateTime getDate(final Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.of("America/Los_Angeles"));
    }

    private static String getDurationText(final Duration duration) {
        final var days = duration.toDaysPart();
        final var hours = duration.toHoursPart();
        final var minutes = duration.toMinutesPart();
        final var seconds = duration.toSecondsPart();
        return days + "d " +
                hours + "h " +
                minutes + "m " +
                seconds + "s";
    }

    public record Metric(String name, String value) {
    }
}
