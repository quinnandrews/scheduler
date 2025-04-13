package io.github.quinnandrews.scheduler.commons.core.domain.constants;

public class JPAEntityConstants {

    private JPAEntityConstants() {
        // no-op
    }

    public static final String ENTITY_CACHE_REGION_PREFIX = "cache.region.";

    public static final String ID_SEQUENCE_SUFFIX = "_id_seq";
    public static final String SEQUENCE_GENERATOR_SUFFIX = "_gen";

    public static final String SERIAL = "SERIAL";
    public static final String BIG_SERIAL = "BIGSERIAL";

    public static final String INT = "INT";
    public static final String BIG_INT = "BIGINT";
    public static final String DOUBLE_PRECISION = "DOUBLE PRECISION";

    public static final String BOOLEAN = "BOOLEAN";

    public static final String TIMESTAMP_WITH_TIME_ZONE = "TIMESTAMP WITH TIME ZONE";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";

    public static final String VARCHAR_2 = "VARCHAR(2)";
    public static final String VARCHAR_16 = "VARCHAR(16)";
    public static final String VARCHAR_32 = "VARCHAR(32)";
    public static final String VARCHAR_64 = "VARCHAR(64)";
    public static final String VARCHAR_128 = "VARCHAR(128)";
    public static final String VARCHAR_255 = "VARCHAR(255)";
}
