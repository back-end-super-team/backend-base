package backend.base.controller.health;

import backend.base.data.api.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/health/v1/cache"})
public class CacheHealthController {

    @SneakyThrows
    @Cacheable(value = "test", key = "1")
    @GetMapping
    public ApiResponse cacheGet() {
        Thread.sleep(3000);
        return ApiResponse.success();
    }

    @CacheEvict(value = "test", key = "1")
    @DeleteMapping
    public ApiResponse cacheDelete() {
        return ApiResponse.success();
    }

    @CachePut(value = "test", key = "1")
    @PostMapping
    public ApiResponse cacheUpdate(@RequestParam String value) {
        return ApiResponse.success(value);
    }

}
