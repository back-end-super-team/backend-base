package backend.backendbase.controller.health;

import backend.backendbase.annotation.RateLimited;
import backend.backendbase.config.cache.DistributedCacheConfig;
import backend.backendbase.controller.BaseController;
import backend.backendbase.data.api.ApiResponse;
import backend.backendbase.enums.RateLimitType;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnBean(DistributedCacheConfig.class)
@AllArgsConstructor
@RestController
@RequestMapping(value = {"/health"})
public class RedisHealthController extends BaseController {

    private RedisTemplate<String, Object> redisTemplate;

    @RateLimited(type = RateLimitType.API, capacity = 1, duration = 10)
    @GetMapping(value = {"/v1/redis"})
    public ApiResponse healthCheckRedis() {
        redisTemplate.opsForValue().get("");
        return ApiResponse.success();
    }

}
