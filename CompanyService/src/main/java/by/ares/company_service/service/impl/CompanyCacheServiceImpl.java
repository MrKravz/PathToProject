package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.service.CompanyCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_KEY;
import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_NAME;

@Service
@RequiredArgsConstructor
public class CompanyCacheServiceImpl implements CompanyCacheService {

    private final CacheManager cacheManager;

    @Override
    public Optional<CompanyDto> getById(Long id) {
        var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
        if (cache == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cache.get(COMPANY_CACHE_KEY + id, CompanyDto.class));
    }

    @Override
    public List<CompanyDto> getAllById(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
        if (cache == null) {
            return Collections.emptyList();
        }
        return ids.stream()
                .map(id -> cache.get(COMPANY_CACHE_KEY + id, CompanyDto.class))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void put(CompanyDto companyDto) {
        var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
        if (cache != null) {
            cache.put(COMPANY_CACHE_KEY + companyDto.getId(), companyDto);
        }
    }

    @Override
    public void putAll(Iterable<CompanyDto> t) {
        t.forEach(this::put);
    }

    @Override
    public void evict(Long id) {
        var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
        if (cache != null) {
            cache.evict(COMPANY_CACHE_KEY + id);
        }
    }

    @Override
    public void evictAll(Iterable<Long> ids) {
        ids.forEach(this::evict);
    }
}
