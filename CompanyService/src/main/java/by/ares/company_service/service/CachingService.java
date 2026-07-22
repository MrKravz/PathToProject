package by.ares.company_service.service;

public interface CachingService<T> {
    void put(T t);

    void putAll(Iterable<T> t);
}
