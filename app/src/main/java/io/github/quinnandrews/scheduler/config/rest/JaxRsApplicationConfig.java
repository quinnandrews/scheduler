package io.github.quinnandrews.scheduler.config.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.springboot.ResteasyAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ImportAutoConfiguration(ResteasyAutoConfiguration.class)
@Configuration
public class JaxRsApplicationConfig {

    @Bean
    public Application jaxRsApplication() {
        return new JaxRsApplication();
    }

    @Bean
    public ObjectMapperContextResolver objectMapperContextResolver(final ObjectMapper objectMapper) {
        return new ObjectMapperContextResolver(objectMapper);
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @ApplicationPath(JaxRsApplication.APPLICATION_PATH)
    public static class JaxRsApplication extends Application {

        public static final String APPLICATION_PATH = "/rest";
    }

    @Provider
    public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper objectMapper;

        public ObjectMapperContextResolver(final ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public ObjectMapper getContext(final Class<?> type) {
            return objectMapper;
        }
    }

    @Provider
    public static class CorsFilter implements ContainerResponseFilter {

        @Override
        public void filter(final ContainerRequestContext request,
                           final ContainerResponseContext response) {
            response.getHeaders().add(
                    "Access-Control-Allow-Origin",
                    "*"
            );
            response.getHeaders().add(
                    "Access-Control-Allow-Headers",
                    "Authorization, Content-Type, x-datadog-parent-id, x-datadog-trace-id, x-datadog-origin, x-datadog-sampling-priority"
            );
            response.getHeaders().add(
                    "Access-Control-Allow-Methods",
                    "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD"
            );
        }
    }
}

