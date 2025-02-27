package backend.backendbase.config.rateLimit;

import backend.backendbase.service.RateLimiterService;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.command.CommandAsyncExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public ProxyManager<String> proxyManagerInMemory() {
        return new CaffeineProxyManager<>(Caffeine.newBuilder().maximumSize(1000), Duration.ofMinutes(10));
    }

    @Primary
    @Bean
    @ConditionalOnExpression("${management.health.redis.enabled}")
    public ProxyManager<String> proxyManagerEngine(CommandAsyncExecutor commandAsyncExecutor) {
        return RedissonBasedProxyManager.builderFor(commandAsyncExecutor).build();
    }

    @Bean
    public RateLimiterService rateLimiterService(ProxyManager<String> proxyManager) {
        return new RateLimiterService(proxyManager);
    }

}
