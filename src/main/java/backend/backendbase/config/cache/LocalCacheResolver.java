package backend.backendbase.config.cache;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryRemovedListener;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class LocalCacheResolver implements CacheResolver {

    private final long expirationTime;
    private final CacheManager cacheManager;
    private final EntryRemovedListener<Object, Object> cacheEntryRemovedListener;
    private final RedissonClient redisson;

    private final Map<String, LocalCache> cacheMap = new ConcurrentHashMap<>();

    @Override
    @Nonnull
    public Collection<? extends Cache> resolveCaches(
            @Nonnull CacheOperationInvocationContext<?> context) {
        Collection<Cache> caches = getCaches(context);
        return caches.stream().map(this::getOrCreateLocalCache).toList();
    }

    private Collection<Cache> getCaches(CacheOperationInvocationContext<?> context) {
        return context.getOperation().getCacheNames().stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .toList();
    }

    private LocalCache getOrCreateLocalCache(Cache cache) {
        return cacheMap.computeIfAbsent(
                cache.getName(),
                cacheName -> new LocalCache(cache, redisson, expirationTime, cacheEntryRemovedListener)
        );
    }

}
