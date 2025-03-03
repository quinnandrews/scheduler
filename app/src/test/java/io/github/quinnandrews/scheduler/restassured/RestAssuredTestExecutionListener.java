package io.github.quinnandrews.scheduler.restassured;

import io.github.quinnandrews.scheduler.config.rest.JaxRsApplicationConfig;
import io.restassured.RestAssured;
import jakarta.servlet.ServletContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class RestAssuredTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(@NotNull final TestContext testContext) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = getContextPath(testContext)
                + JaxRsApplicationConfig.JaxRsApplication.APPLICATION_PATH;
    }

    private String getContextPath(final TestContext testContext) {
        return testContext.getApplicationContext()
                .getBean(ServletContext.class)
                .getContextPath();
    }
}
