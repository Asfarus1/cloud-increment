package asfarus.example.cloudincrement.repository.internal;

import asfarus.example.cloudincrement.repository.IncrementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static asfarus.example.cloudincrement.repository.internal.JdbcIncrementRepository.CACHE_INC_KEY;
import static asfarus.example.cloudincrement.repository.internal.JdbcIncrementRepository.CACHE_INC_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "UPDATE inc SET val = 0")
class JdbcIncrementRepositoryTest {

    @Autowired
    private IncrementRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        Optional.ofNullable(cacheManager.getCache(CACHE_INC_NAME))
                .ifPresent(Cache::clear);
    }

    @Test
    void get() {
        assertThat(repository.get()).isEqualTo(0L);
        assertCacheHasValue(0L);
    }

    @Test
    @Disabled("H2 not supported UPDATE with RETURNING")
    void incrementAndGet() {
        assertThat(repository.incrementAndGet()).isEqualTo(1L);
        assertCacheHasValue(1L);
    }

    @Test
    void reset() {
        putValueIntoCache();
        repository.reset();
        assertCacheHasNoValue();
    }

    @Test
    void cacheExpireAfter1s() throws InterruptedException {
        putValueIntoCache();
        TimeUnit.SECONDS.sleep(1);
        assertCacheHasNoValue();
    }

    private void assertCacheHasValue(Long val) {
        Cache cache = cacheManager.getCache(CACHE_INC_NAME);
        assertThat(cache).isNotNull();
        Cache.ValueWrapper actual = cache.get(CACHE_INC_KEY);
        assertThat(actual).isNotNull();
        assertThat(actual.get()).isEqualTo(val);
    }

    private void putValueIntoCache() {
        Cache cache = cacheManager.getCache(CACHE_INC_NAME);
        assertThat(cache).isNotNull();
        cache.put(CACHE_INC_KEY, 1L);
    }

    private void assertCacheHasNoValue() {
        Cache cache = cacheManager.getCache(CACHE_INC_NAME);
        assertThat(cache).isNotNull();
        Cache.ValueWrapper actual = cache.get(CACHE_INC_KEY);
        assertThat(actual).isNull();
    }
}