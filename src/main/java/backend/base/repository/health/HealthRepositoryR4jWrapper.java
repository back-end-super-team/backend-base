package backend.base.repository.health;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static backend.base.utility.Resilience4jConstant.R4J_HEALTHCHECK_DB;

@Component
@RequiredArgsConstructor
public class HealthRepositoryR4jWrapper {

    private final HealthRepository healthRepository;

    @TimeLimiter(name = R4J_HEALTHCHECK_DB)
    @Bulkhead(name = R4J_HEALTHCHECK_DB)
    @CircuitBreaker(name = R4J_HEALTHCHECK_DB)
    public Mono<Boolean> healthCheckWrapper() {
        return Mono.just(healthRepository.healthCheck());
    }

    public Boolean healthCheck() {
        return this.healthCheckWrapper().block();
    }

}
