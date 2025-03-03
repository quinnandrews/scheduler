package io.github.quinnandrews.scheduler.timezones;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DefaultTimeZoneTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(@NotNull final TestContext testContext) {
        TimeZoneUtil.setUTCAsDefault();
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        TimeZoneUtil.resetDefault();
    }
}
