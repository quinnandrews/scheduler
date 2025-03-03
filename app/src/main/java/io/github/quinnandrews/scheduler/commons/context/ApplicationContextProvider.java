package io.github.quinnandrews.scheduler.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /*
    Suppressing SonarLint warning about setting a static variable with
    a non-static method. In this case it is OK.
     */
    @SuppressWarnings("java:S2696")
    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return getApplicationContext().getEnvironment();
    }

    public static <T> T getBean(final Class<T> type) {
        return getApplicationContext().getBean(type);
    }
}
