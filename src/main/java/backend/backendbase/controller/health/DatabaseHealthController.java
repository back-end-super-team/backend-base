package backend.backendbase.controller.health;

import backend.backendbase.annotation.RateLimited;
import backend.backendbase.common.result.Result;
import backend.backendbase.enums.RateLimitType;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = {"/health"})
public class DatabaseHealthController {

    private EntityManager entityManager;

    @RateLimited(type = RateLimitType.API, capacity = 1, duration = 10)
    @GetMapping(value = {"/v1/db"})
    public Result<Void> healthCheckDB() {
        return Result.judge(entityManager.unwrap(Session.class).isConnected());
    }
}
