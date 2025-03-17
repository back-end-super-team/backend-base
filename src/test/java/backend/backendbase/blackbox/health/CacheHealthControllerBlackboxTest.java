package backend.backendbase.blackbox.health;

import backend.backendbase.blackbox.BlackboxTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedissonClient redissonClient;

    @BeforeEach
    public void init() {
        Objects.requireNonNull(cacheManager.getCache("in-memory")).clear();
        redissonClient.getMapCache("in-memory").clear();
    }

    @Test
    public void getCache() {
        Instant start = Instant.now();
        given()
                .log().all()
                .when()
                .get("/health/v1/cache")
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() > 3000);

        start = Instant.now();
        given()
                .log().all()
                .when()
                .get("/health/v1/cache")
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
                .get("/health/v1/cache")
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
        assertTrue(Duration.between(start, Instant.now()).toMillis() > 3000);

        given()
                .log().all()
                .when()
                .delete("/health/v1/cache")
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        start = Instant.now();
        given()
                .log().all()
                .when()
                .get("/health/v1/cache")
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
                .post("/health/v1/cache?value=" + value)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        Instant start = Instant.now();
        given()
                .log().all()
                .when()
                .get("/health/v1/cache")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body("payload", equalTo(value))
        ;
        System.out.println(Duration.between(start, Instant.now()).toMillis());
        assertTrue(Duration.between(start, Instant.now()).toMillis() < 3000);
    }

}
