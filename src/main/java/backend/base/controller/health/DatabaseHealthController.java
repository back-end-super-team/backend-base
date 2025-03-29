package backend.base.controller.health;

import backend.base.annotation.RateLimited;
import backend.base.data.api.ApiResponse;
import backend.base.enums.RateLimitType;
import backend.base.repository.health.HealthRepositoryR4jWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = {"/health"})
public class DatabaseHealthController {

    private HealthRepositoryR4jWrapper healthRepositoryR4jWrapper;

    @RateLimited(type = RateLimitType.API, capacity = 1, duration = 10)
    @GetMapping(value = {"/v1/db"})
    public ApiResponse healthCheckDB() {
        return ApiResponse.success(healthRepositoryR4jWrapper.healthCheck());
    }

}
