package backend.base.controller.health;

import backend.base.annotation.RateLimited;
import backend.base.config.cache.DistributedCacheConfig;
import backend.base.controller.BaseController;
import backend.base.data.api.ApiResponse;
import backend.base.enums.RateLimitType;
import io.swagger.v3.oas.annotations.Hidden;
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

    @Hidden
    @RateLimited(type = RateLimitType.API, capacity = 1, duration = 10)
    @GetMapping(value = {"/v1/redis"})
    public ApiResponse healthCheckRedis() {
        redisTemplate.opsForValue().get("");
        return ApiResponse.success();
    }

}
