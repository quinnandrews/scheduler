package io.github.quinnandrews.scheduler.commons.core.domain.encoding;

import org.sqids.Sqids;

import java.util.List;
import java.util.Objects;

public class IdEncoder {

    private static final Sqids sqids = Sqids.builder().build();

    private IdEncoder() {
        // no-op
    }

    public static String encode(final List<Long> numbers) {
        Objects.requireNonNull(numbers, "Argument 'numbers' must not be null.");
        return sqids.encode(numbers);
    }

    public static List<Long> decode(final String id) {
        Objects.requireNonNull(id, "Argument 'id' must not be null.");
        return sqids.decode(id);
    }
}
