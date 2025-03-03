package io.github.quinnandrews.scheduler.modules.administration.schedules.rest;

import io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model.LocationListResponse;
import io.github.quinnandrews.scheduler.restassured.RestAssuredTest;
import io.github.quinnandrews.spring.local.postgresql.config.EnableLocalPostgreSQL;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.github.quinnandrews.scheduler.restassured.RestAssuredTestUtil.jsonSchemaPathOf;
import static io.github.quinnandrews.scheduler.restassured.RestAssuredTestUtil.pathOf;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

@EnableLocalPostgreSQL
@RestAssuredTest
@DirtiesContext
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocationControllerTest {

    @LocalServerPort
    private Integer port;

    @Test
    void createLocation() {
    }

    @Test
    void updateLocation() {
    }

    @Test
    void getLocation() {
    }

    @Test
    void getAllLocations() {
        given().port(port)
                .when().get(pathOf(LocationController.class, "getAllLocations"))
                .then().time(lessThan(1000L))
                .and().statusCode(HttpStatus.OK.value())
                .and().contentType(ContentType.JSON)
                .and().body(matchesJsonSchemaInClasspath(jsonSchemaPathOf(LocationListResponse.class)))
                .and().body("locations.size()", equalTo(0))
        //.and().body("[0].name", equalTo("NAME"))
        ;
    }
}