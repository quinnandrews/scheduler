package io.github.quinnandrews.scheduler.commons.snapshots.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javers.core.metamodel.annotation.TypeName;
import org.javers.core.metamodel.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.TIMESTAMP_WITH_TIME_ZONE;

@Value
@TypeName("commons.snapshots.AuthorSummary")
@Embeddable
public class AuthorSummary {

    @CreatedDate
    @Column(name = "date_created",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false,
            updatable = false)
    private Instant dateCreated;

    @LastModifiedDate
    @Column(name = "date_last_modified",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false)
    private Instant dateLastModified;

    public AuthorSummary() {
        // no-op
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Instant getDateLastModified() {
        return dateLastModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dateCreated", getDateCreated())
                .append("dateLastModified", getDateLastModified())
                .toString();
    }
}
