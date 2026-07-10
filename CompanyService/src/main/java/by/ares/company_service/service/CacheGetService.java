package by.ares.company_service.service;

import java.util.List;
import java.util.Optional;

public interface CacheGetService<T, R> {
    Optional<T> getById(R id);

    List<T> getAllById(List<R> ids);
}
