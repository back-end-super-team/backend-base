package backend.backendbase.controller.health;

import backend.backendbase.data.api.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/health"})
public class CacheCheckController {

    @Hidden
    @SneakyThrows
    @Cacheable(value = "in-memory")
    @GetMapping(value = "/cache")
    public ApiResponse cacheGet() {
        Thread.sleep(3000);
        return ApiResponse.ok();
    }

    @Hidden
    @CacheEvict(value = "in-memory")
    @DeleteMapping(value = "/cache")
    public ApiResponse Get() {
        return ApiResponse.ok();
    }

}
