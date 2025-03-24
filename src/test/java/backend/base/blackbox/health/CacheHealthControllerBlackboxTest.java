package backend.base.blackbox.health;

import backend.base.blackbox.BlackboxTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@BlackboxTest
public class CacheHealthControllerBlackboxTest {

    private final String PATH = "/health/v1/cache";

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void init() {
        cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

    @Test
    public void getCache() {
        Instant start = Instant.now();
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() > 3000);

        start = Instant.now();
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() < 100);
    }

    @Test
    public void cacheDelete() {
        Instant start = Instant.now();
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() > 3000);

        given()
                .log().all()
                .when()
                .delete(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        start = Instant.now();
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() > 3000);
    }

    @Test
    public void cachePut() {
        String value = UUID.randomUUID().toString();

        given()
                .log().all()
                .when()
                .post(PATH + "?value=" + value)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        Instant start = Instant.now();
        given()
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body("payload", equalTo(value))
        ;
        System.out.println(Duration.between(start, Instant.now()).toMillis());
        assertTrue(Duration.between(start, Instant.now()).toMillis() < 3000);
    }

}
