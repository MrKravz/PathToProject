package by.ares.company_service.unit;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.service.impl.CompanyCacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Set;

import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_KEY;
import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_CACHE_NAME;
import static by.ares.company_service.util.TestConstants.EXISTING_COMPANY_ID;
import static by.ares.company_service.util.TestConstants.NOT_EXISTING_COMPANY_ID;
import static by.ares.company_service.util.TestModelsBuilder.buildCompanyDto;
import static by.ares.company_service.util.TestModelsBuilder.mockCompaniesDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyCacheServiceImplTest {

    @Mock
    private Cache cache;
    @Mock
    private CacheManager cacheManager;
    @InjectMocks
    private CompanyCacheServiceImpl companyCacheService;

    private CompanyDto companyDto;
    private Set<CompanyDto> companies;

    @BeforeEach
    void init() {
        companyDto = buildCompanyDto();
        companies = mockCompaniesDto();
        lenient().when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Test
    void getById() {
        when(cacheManager.getCache(COMPANY_CACHE_NAME)).thenReturn(cache);
        when(cache.get(COMPANY_CACHE_KEY + EXISTING_COMPANY_ID, CompanyDto.class))
                .thenReturn(companyDto);
        var result = companyCacheService.getById(EXISTING_COMPANY_ID);
        assertEquals(companyDto, result.get());
        verify(cacheManager).getCache(COMPANY_CACHE_NAME);
        verify(cache).get(COMPANY_CACHE_KEY + EXISTING_COMPANY_ID, CompanyDto.class);
    }

    @Test
    void getAllById() {
        when(cacheManager.getCache(COMPANY_CACHE_NAME)).thenReturn(cache);
        companies.forEach(x ->
                when(cache.get(COMPANY_CACHE_KEY + x.getId(), CompanyDto.class))
                        .thenReturn(x)
        );
        var result = companyCacheService.getAllById(List.of(EXISTING_COMPANY_ID, NOT_EXISTING_COMPANY_ID));
        assertEquals(2, (long) result.size());
        verify(cacheManager).getCache(COMPANY_CACHE_NAME);
        companies.forEach(x ->
                verify(cache).get(COMPANY_CACHE_KEY + x.getId(), CompanyDto.class)
        );
    }

    @Test
    void put() {
        when(cacheManager.getCache(COMPANY_CACHE_NAME)).thenReturn(cache);
        companyCacheService.put(companyDto);
        verify(cacheManager).getCache(COMPANY_CACHE_NAME);
        verify(cache).put(COMPANY_CACHE_KEY + EXISTING_COMPANY_ID, companyDto);
    }

    @Test
    void putAll() {
        when(cacheManager.getCache(COMPANY_CACHE_NAME)).thenReturn(cache);
        companyCacheService.putAll(companies);
        verify(cacheManager, times(companies.size())).getCache(COMPANY_CACHE_NAME);
        companies.forEach(x ->
                verify(cache).put(COMPANY_CACHE_KEY + x.getId(), x)
        );
    }

    @Test
    void evict() {
        companyCacheService.evict(EXISTING_COMPANY_ID);
        verify(cache).evict(COMPANY_CACHE_KEY + companyDto.getId());
    }

    @Test
    void evictAll() {
        companyCacheService.evictAll(List.of(EXISTING_COMPANY_ID, NOT_EXISTING_COMPANY_ID));
        companies.forEach(x ->
                verify(cache).evict(COMPANY_CACHE_KEY + companyDto.getId())
        );
    }

}