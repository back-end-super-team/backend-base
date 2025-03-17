package backend.backendbase.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class CacheManagerConfig {

    @Value("${spring.cache.in-memory.cache-name:in-memory}")
    private String cacheName;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache(cacheName, Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .recordStats()
                .build());
        return cacheManager;
    }

}
