package io.github.quinnandrews.scheduler.config.rest;

import io.github.quinnandrews.scheduler.commons.rest.annotations.PublicAPI;
import io.github.quinnandrews.scheduler.commons.rest.annotations.StaffAPI;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class OpenApiDocumentConfig {

    public static final String PUBLIC_LOCATION_TAG = "Locations";
    public static final String RESTRICTED_LOCATION_ADMIN_TAG = "Administration: Location Configuration";

    private final String contextPath;
    private final String applicationVersion;
    private final String restApiPackage;

    public OpenApiDocumentConfig(@Value("${server.servlet.context-path}")
                                 final String contextPath,
                                 @Value("${example.application.version}")
                                 final String applicationVersion,
                                 @Value("${example.application.rest-api.package}")
                                 final String restApiPackage) {
        this.contextPath = contextPath;
        this.applicationVersion = applicationVersion;
        this.restApiPackage = restApiPackage;
    }

    @Bean("publicOpenApiConfig")
    public SwaggerConfiguration publicOpenApiConfiguration() {
        final var info = new Info()
                .title("Public API")
                .version(applicationVersion)
                .description("Available to public-facing applications.");
        final var openAPI = new OpenAPI()
                .info(info)
                .addTagsItem(new Tag().name(PUBLIC_LOCATION_TAG)
                        .description("""
                                Provides read-only access to active Locations. Inactive \
                                Locations are not exposed through this API."""))
                .addServersItem(new Server().url(contextPath));
        return new SwaggerConfiguration()
                .id(PublicAPI.class.getSimpleName())
                .resourceClasses(findResourceClassesAnnotatedBy(PublicAPI.class))
                .openAPI(openAPI);
    }

    @Bean("restrictedOpenApiConfig")
    public SwaggerConfiguration restrictedOpenApiConfiguration() {
        final var info = new Info()
                .title("Restricted API")
                .version(applicationVersion)
                .description("""
                        Only available to employee-facing applications. Provides a \
                        range of capabilities unavailable through the Public API.""");
        final var openAPI = new OpenAPI()
                .info(info)
                .addTagsItem(new Tag().name(RESTRICTED_LOCATION_ADMIN_TAG)
                        .description("Defines and maintains Locations, active or inactive."))
                .addServersItem(new Server().url(contextPath));
        return new SwaggerConfiguration().openAPI(openAPI)
                .id(StaffAPI.class.getSimpleName())
                .resourceClasses(findResourceClassesAnnotatedBy(StaffAPI.class))
                .openAPI(openAPI);
    }

    @SafeVarargs
    private Set<String> findResourceClassesAnnotatedBy(final Class<? extends Annotation>... annotations) {
        return findComponentsAnnotatedBy(annotations).stream()
                .map(BeanDefinition::getBeanClassName)
                .collect(Collectors.toSet());
    }

    @SafeVarargs
    private Set<BeanDefinition> findComponentsAnnotatedBy(final Class<? extends Annotation>... annotations) {
        final var componentProvider = new ClassPathScanningCandidateComponentProvider(false);
        Arrays.stream(annotations).forEach(a -> componentProvider.addIncludeFilter(new AnnotationTypeFilter(a)));
        return componentProvider.findCandidateComponents(restApiPackage);
    }
}
