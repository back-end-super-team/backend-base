package backend.backendbase.config.cache;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@EnableCaching
@RequiredArgsConstructor
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LocalCacheConfig implements CachingConfigurer {

    @Value("${spring.cache.in-memory.expiration-time:60}")
    private Long expirationTime;

    private final ObjectProvider<CacheManager> cacheManager;
    private final ObjectProvider<RedissonClient> redissonClient;

    @Override
    public CacheResolver cacheResolver() {
        return new LocalCacheResolver(
                expirationTime,
                cacheManager.getObject(),
                event -> {
                    Cache cache = cacheManager.getObject().getCache(event.getSource().getName());
                    if (cache != null) {
                        cache.evict(event.getKey());
                    }
                },
                redissonClient.getObject()
        );
    }

}
