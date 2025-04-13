package io.github.quinnandrews.scheduler.commons.core.domain.caching;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public @interface ReadOnlyCacheRegion {

    @AliasFor(annotation = Cache.class)
    String region() default "";
}
