package backend.backendbase.controller.health;

import backend.backendbase.common.result.Result;
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

    @SneakyThrows
    @Cacheable(value = "in-memory")
    @GetMapping(value = "/cache")
    public Result<Void> cacheGet() {
        Thread.sleep(3000);
        return Result.success();
    }

    @CacheEvict(value = "in-memory")
    @DeleteMapping(value = "/cache")
    public Result<Void> Get() {
        return Result.success();
    }

}
