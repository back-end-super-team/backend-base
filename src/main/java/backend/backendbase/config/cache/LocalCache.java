package backend.backendbase.config.cache;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryRemovedListener;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class LocalCache implements Cache {

    private final long expirationTime;
    private final Cache cache;
    private final RMapCache<Object, Object> distributedCache;



    public LocalCache(Cache cache,
                      RedissonClient redisson,
                      long expirationTime,
                      EntryRemovedListener<?, ?> entryRemovedListener) {
        this.cache = cache;
        this.expirationTime = expirationTime;
        this.distributedCache = redisson.getMapCache(getName());
        this.distributedCache.addListener(entryRemovedListener);
    }

    @Override
    @Nonnull
    public String getName() {
        return cache.getName();
    }

    @Override
    @Nonnull
    public Object getNativeCache() {
        return cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(@Nonnull Object key) {
        Object value = cache.get(key);

        if (value == null && (value = distributedCache.get(key)) != null) {
            cache.put(key, value);
        }

        return toValueWrapper(value);
    }

    private ValueWrapper toValueWrapper(Object value) {
        if (value == null) return null;
        return value instanceof ValueWrapper ? (ValueWrapper) value : new SimpleValueWrapper(value);
    }

    @Override
    public <T> T get(@Nonnull Object key, Class<T> type) {
        return cache.get(key, type);
    }

    @Override
    public <T> T get(@Nonnull Object key, @Nonnull Callable<T> valueLoader) {
        return cache.get(key, valueLoader);
    }

    @Override
    public void put(@Nonnull Object key, Object value) {
        distributedCache.put(key, value, expirationTime, TimeUnit.MINUTES);
        cache.put(key, value);
    }

    @Override
    public void evict(@Nonnull Object key) {
        distributedCache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

}
