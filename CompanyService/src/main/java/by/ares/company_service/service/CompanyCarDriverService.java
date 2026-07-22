package by.ares.company_service.service;

import by.ares.company_service.dto.CompanyDto;

public interface CompanyCarDriverService extends CompanyResourceAssigner {
    CompanyDto assign(Long companyId, Long carDriverId);

    CompanyDto unassign(Long companyId, Long carDriverId);
}
