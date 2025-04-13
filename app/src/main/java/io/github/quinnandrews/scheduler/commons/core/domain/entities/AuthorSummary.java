package io.github.quinnandrews.scheduler.commons.core.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.TIMESTAMP_WITH_TIME_ZONE;

@Embeddable
public class AuthorSummary {

    @CreatedDate
    @Column(name = "date_created",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false,
            updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_last_modified",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false)
    private LocalDateTime dateLastModified;

    public AuthorSummary() {
        // no-op
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateLastModified() {
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
