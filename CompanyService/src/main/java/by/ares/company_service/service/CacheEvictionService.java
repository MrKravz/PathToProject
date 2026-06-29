package by.ares.company_service.service;

public interface CacheEvictionService<T> {
    void evict(T t);
    void evictAll(Iterable<T> iterable);
}
