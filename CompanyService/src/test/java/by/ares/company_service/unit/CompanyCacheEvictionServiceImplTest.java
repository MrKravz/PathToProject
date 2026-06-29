package by.ares.company_service.unit;

import by.ares.company_service.model.Company;
import by.ares.company_service.service.impl.CompanyCacheEvictionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_KEY;
import static by.ares.company_service.util.TestModelsBuilder.buildCompany;
import static by.ares.company_service.util.TestModelsBuilder.mockCompanies;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyCacheEvictionServiceImplTest {

    @Mock
    private Cache cache;
    @Mock
    private CacheManager cacheManager;
    @InjectMocks
    private CompanyCacheEvictionServiceImpl companyCacheEvictionService;

    private Company company;
    private Iterable<Company> companies;

    @BeforeEach
    void init() {
        company = buildCompany();
        companies = mockCompanies();
        lenient().when(cacheManager.getCache(anyString())).thenReturn(cache);
    }


    @Test
    void evict() {
        companyCacheEvictionService.evict(company);
        verify(cache).evict(COMPANY_CACHE_KEY + company.getId());
    }

    @Test
    void evictAll() {
        companyCacheEvictionService.evictAll(companies);
        companies.forEach(x ->
                verify(cache).evict(COMPANY_CACHE_KEY + company.getId())
        );
    }

}