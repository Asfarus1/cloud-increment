package asfarus.example.cloudincrement.repository.internal;

import asfarus.example.cloudincrement.repository.IncrementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcIncrementRepository implements IncrementRepository {

    public static final String CACHE_INC_NAME = "inc";
    public static final String CACHE_INC_KEY = "inc";
    public static final String KEY = "'inc'";

    private final JdbcTemplate template;
    private final ResultSetExtractor<Long> valueExtractor = rs -> rs.next() ? rs.getLong(1) : 0;

    @Override
    @Cacheable(value = CACHE_INC_NAME, key = KEY)
    public Long get() {
        log.debug("JdbcIncrementRepository.get()");
        return template.query("SELECT val FROM inc",valueExtractor);
    }

    @Override
    @CachePut(value = CACHE_INC_NAME, key = KEY)
    public Long incrementAndGet() {
        log.debug("JdbcIncrementRepository.incrementAndGet()");
        return template.query("UPDATE inc SET val = val + 1 RETURNING val", valueExtractor);
    }

    @Override
    @CacheEvict(value = CACHE_INC_NAME, key = KEY)
    public void reset() {
        log.debug("JdbcIncrementRepository.reset()");
        template.execute("UPDATE inc SET val = 0");
    }
}
