package asfarus.example.cloudincrement.repository;

public interface IncrementRepository {
    Long get();

    Long incrementAndGet();

    void reset();
}
