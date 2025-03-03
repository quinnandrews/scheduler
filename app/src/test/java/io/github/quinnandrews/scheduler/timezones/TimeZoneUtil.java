package io.github.quinnandrews.scheduler.timezones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.TimeZone;

public class TimeZoneUtil {

    private static final Logger logger = LoggerFactory.getLogger(TimeZoneUtil.class);

    public static void setUTCAsDefault() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));
        logger.info(
                "System default TimeZone set to: {} ({})",
                TimeZone.getDefault().getDisplayName(),
                TimeZone.getDefault().getID()
        );
    }

    public static void resetDefault() {
        TimeZone.setDefault(null);
        logger.info(
                "System default TimeZone reset to: {} ({})",
                TimeZone.getDefault().getDisplayName(),
                TimeZone.getDefault().getID()
        );
    }
}
