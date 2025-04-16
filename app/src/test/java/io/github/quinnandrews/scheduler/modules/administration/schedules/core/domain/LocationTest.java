package io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.jparams.verifier.tostring.preset.Presets;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void fluentApi_setsValuesCorrectly() {
        var id = 1L;
        var version = 2;
        var name = "Portland";
        var state = "OR";
        var timeZone = "America/Los_Angeles";
        var latitude = 45.523064;
        var longitude = -122.676483;
        var radius = 60;
        var status = Location.Status.ACTIVE;
        var dateCreated = LocalDateTime.now().minusDays(1);
        var dateLastModified = LocalDateTime.now();
        var employeeCreatedBy = "employee1@example.com";
        var employeeLastModifiedBy = "employee2@example.com";
        // given fields exposed through the fluent-api are set with the fluent-api
        var location = new Location().withId(id)
                .withVersion(version)
                .withName(name)
                .withState(state)
                .withTimeZone(timeZone)
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withRadius(radius)
                .withStatus(status);
        // and fields not exposed through the fluent-api are set with reflection
        //ReflectionTestUtils.setField(location, "dateCreated", dateCreated);
        //ReflectionTestUtils.setField(location, "dateLastModified", dateLastModified);
//        ReflectionTestUtils.setField(location, "employeeCreatedBy", employeeCreatedBy);
//        ReflectionTestUtils.setField(location, "employeeLastModifiedBy", employeeLastModifiedBy);
        // then the getters providing access to the fields match the values set
        assertEquals(id, location.getId());
        assertEquals(version, location.getVersion());
        assertEquals(name, location.getName());
        assertEquals(state, location.getState());
        assertEquals(timeZone, location.getTimeZone());
        assertEquals(latitude, location.getLatitude());
        assertEquals(longitude, location.getLongitude());
        assertEquals(radius, location.getRadius());
        assertEquals(status, location.getStatus());
        //assertEquals(dateCreated, location.getDateCreated());
        //assertEquals(dateLastModified, location.getDateLastModified());
//        assertEquals(employeeCreatedBy, location.getEmployeeCreatedBy());
//        assertEquals(employeeLastModifiedBy, location.getEmployeeLastModifiedBy());
    }

    @Test
    void equalsAndHashCode() {
        var id = 1L;
        // equal when ids match
        var first = new Location().withId(id);
        var second = new Location().withId(id);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        // not equal when ids do not match
        second.withId(2L);
        assertNotEquals(first, second);
        assertNotEquals(first.hashCode(), second.hashCode());
        // not equal when something other than a Location
        var obj = new Object();
        assertNotEquals(first, obj);
        assertNotEquals(first.hashCode(), obj.hashCode());
    }

    @Test
    void toString_hasValidFields() {
        ToStringVerifier.forClass(Location.class)
                .withPreset(Presets.APACHE_TO_STRING_BUILDER_MULTI_LINE_STYLE)
                .withFailOnExcludedFields(Boolean.TRUE)
                .verify();
    }

    @Test
    void isActive() {
        // given a Location with a null status
        var location = new Location().withStatus(null);
        // then isActive returns FALSE
        assertFalse(location.isActive());
        // but if the Location's status is set to ACTIVE
        location.withStatus(Location.Status.ACTIVE);
        // then isActive returns TRUE
        assertTrue(location.isActive());
        // and if the Location's status is set to FALSE
        location.withStatus(Location.Status.INACTIVE);
        // then isActive returns FALSE
        assertFalse(location.isActive());
    }
}