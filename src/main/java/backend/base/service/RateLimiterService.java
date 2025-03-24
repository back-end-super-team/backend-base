package backend.base.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class RateLimiterService {

    private final ProxyManager<String> proxyManager;

    public Bucket resolveBucket(String key, int capacity, long durationSeconds) {
        Supplier<BucketConfiguration> configSupplier = () -> {
            Bandwidth limit = Bandwidth.builder().capacity(capacity).refillGreedy(capacity, Duration.ofSeconds(durationSeconds)).build();
            return BucketConfiguration.builder()
                    .addLimit(limit)
                    .build();
        };
        return proxyManager.builder().build(key, configSupplier);
    }

    public boolean tryConsume(String key, int capacity, long durationSeconds) {
        Bucket bucket = resolveBucket(key, capacity, durationSeconds);
        return bucket.tryConsume(1);
    }

    public void resetBucket(String key) {
        proxyManager.removeProxy(key);
    }

}
