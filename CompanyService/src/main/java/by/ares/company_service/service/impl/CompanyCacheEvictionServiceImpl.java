package by.ares.company_service.service.impl;

import by.ares.company_service.model.Company;
import by.ares.company_service.service.CacheEvictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_KEY;
import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_NAME;

@Service
@RequiredArgsConstructor
public class CompanyCacheEvictionServiceImpl implements CacheEvictionService<Company> {

    private final CacheManager cacheManager;

    @Override
    public void evict(Company company) {
        var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
        if (cache != null) {
            cache.evict(COMPANY_CACHE_KEY + company.getId());
        }
    }

    @Override
    public void evictAll(Iterable<Company> iterable) {
        iterable.forEach(this::evict);
    }
}
