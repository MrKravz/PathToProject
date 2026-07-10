package by.ares.company_service.service;

import by.ares.company_service.dto.CompanyDto;

public interface CompanyCacheService extends CacheGetService<CompanyDto, Long>, CachingService<CompanyDto>,
        CacheEvictionService<Long> {
}
