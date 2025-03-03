package io.github.quinnandrews.scheduler.restassured;

import jakarta.ws.rs.Path;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

public class RestAssuredTestUtil {

    public static String pathOf(final Class<?> clazz, final String method) {
        try {
            return pathOf(clazz.getMethod(method));
        } catch (final NoSuchMethodException e) {
            throw new RestAssuredTestException(e);
        }
    }

    public static String pathOf(final Method method) {
        return getClassPathValue(method.getDeclaringClass())
                + getMethodPathValue(method);
    }

    private static String getClassPathValue(final Class<?> clazz) {
        return clazz.isAnnotationPresent(Path.class) ? clazz.getAnnotation(Path.class).value() : "";
    }

    private static String getMethodPathValue(final Method method) {
        return method.isAnnotationPresent(Path.class) ? method.getAnnotation(Path.class).value() : "";
    }

    public static String jsonSchemaPathOf(final Class<?> clazz) {
        final var filePath = StringUtils.replace(clazz.getName(), ".", "/");
        return "schema/json/" + filePath + ".schema";
    }
}
