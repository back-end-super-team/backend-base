package backend.base.config.rateLimit;

import backend.base.service.RateLimiterService;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.command.CommandAsyncExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RateLimitConfig {

    @Primary
    @Bean
    public ProxyManager<String> proxyManagerInMemoryDataStorage(CommandAsyncExecutor commandAsyncExecutor) {
        return RedissonBasedProxyManager.builderFor(commandAsyncExecutor).build();
    }

    @Bean
    public RateLimiterService rateLimiterService(ProxyManager<String> proxyManager) {
        return new RateLimiterService(proxyManager);
    }

}
