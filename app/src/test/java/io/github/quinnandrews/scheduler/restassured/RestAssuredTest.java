package io.github.quinnandrews.scheduler.restassured;

import io.github.quinnandrews.scheduler.timezones.DefaultTimeZoneTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestExecutionListeners(listeners = {
        RestAssuredTestExecutionListener.class,
        DefaultTimeZoneTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface RestAssuredTest {
}
