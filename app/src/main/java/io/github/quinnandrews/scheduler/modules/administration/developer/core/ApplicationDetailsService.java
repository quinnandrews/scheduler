package io.github.quinnandrews.scheduler.modules.administration.developer.core;

import io.github.quinnandrews.scheduler.modules.administration.developer.core.domain.ApplicationDetails;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
public class ApplicationDetailsService {

    private final ApplicationContext applicationContext;
    private final Environment environment;
    private final BuildProperties buildProperties;
    private final MeterRegistry meterRegistry;

    public ApplicationDetailsService(final ApplicationContext applicationContext,
                                     final Environment environment,
                                     final BuildProperties buildProperties,
                                     final MeterRegistry meterRegistry) {
        this.applicationContext = applicationContext;
        this.environment = environment;
        this.buildProperties = buildProperties;
        this.meterRegistry = meterRegistry;
    }

    public ApplicationDetails getApplicationDetails() {
        final var request = (
                (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
        ).getRequest();
        return new ApplicationDetails(
                request.getServerName().toUpperCase(),
                request.getServerPort(),
                request.getContextPath(),
                buildProperties.getName(),
                buildProperties.get("project.description"),
                buildProperties.getGroup(),
                buildProperties.getArtifact(),
                buildProperties.getVersion(),
                buildProperties.getTime(),
                Instant.ofEpochMilli(applicationContext.getStartupDate()),
                JavaVersion.getJavaVersion().toString(),
                SpringVersion.getVersion(),
                SpringBootVersion.getVersion(),
                Arrays.toString(environment.getActiveProfiles())
                        .replace("[", "")
                        .replace("]", ""),
                getApplicationMetrics());
    }

    public Map<String, String> getApplicationMetrics() {
        final var cpu = meterRegistry.get("process.cpu.usage").gauge().value();
        final var memory = meterRegistry.get("jvm.memory.used").gauge().value();
        final var threads = meterRegistry.get("jvm.threads.live").gauge().value();
        return Map.of(
                "process.cpu.usage", Math.round((cpu * 100D) * 100D) / 100D + "%",
                "jvm.memory.used", Math.round((memory / 1E+6) * 100D) / 100D + "mb",
                "jvm.threads.live", String.valueOf(Double.valueOf(threads).intValue())
        );
    }
}
