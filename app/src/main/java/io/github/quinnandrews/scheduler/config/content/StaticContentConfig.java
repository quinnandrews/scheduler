package io.github.quinnandrews.scheduler.config.content;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;

/**
 * <p> Handles static resource paths that do not include a trailing '/index.html' by
 * resolving the path to '/index.html' by default.
 *
 * <p> Based on a Stack Overflow <a href="https://stackoverflow.com/a/69647129">answer</a>
 * provided by Bojan Vukasovic.
 */
@Configuration
public class StaticContentConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.setOrder(Ordered.LOWEST_PRECEDENCE)
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(Boolean.TRUE) // first time resolved, then the route will be retrieved from a cache
                .addResolver(new IndexFallbackResourceResolver());
    }

    static class IndexFallbackResourceResolver extends PathResourceResolver {
        @Override
        protected Resource resolveResourceInternal(@Nullable
                                                   final HttpServletRequest request,
                                                   @NonNull
                                                   final String requestPath,
                                                   @NonNull
                                                   final List<? extends Resource> locations,
                                                   @NonNull
                                                   final ResourceResolverChain chain) {
            final var resource = super.resolveResourceInternal(request, requestPath, locations, chain);
            if (resource == null) {
                return super.resolveResourceInternal(request, requestPath + "/index.html", locations, chain);
            }
            return resource;
        }
    }
}
