package backend.base.blackbox.health;

import backend.base.blackbox.BlackboxTest;
import backend.base.enums.ResponseEnum;
import backend.base.service.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@BlackboxTest
public class DatabaseHealthControllerBlackboxTest {

    private final String PATH = "/health/v1/db";

    @Autowired
    private RateLimiterService rateLimiterService;

    @BeforeEach
    public void init() {
        rateLimiterService.resetBucket(PATH);
    }

    @Test
    void healthCheckDB () {
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
    }

    @Test
    void healthCheckDB_rateLimited () {
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .log().all()
                .statusCode(ResponseEnum.TOO_MANY_REQUESTS.getCode())
                .body("code", equalTo(ResponseEnum.TOO_MANY_REQUESTS.getCode()))
                .body("message", equalTo(ResponseEnum.TOO_MANY_REQUESTS.getMessage()))
        ;
    }
}
