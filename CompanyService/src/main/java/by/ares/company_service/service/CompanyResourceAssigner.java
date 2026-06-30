package by.ares.company_service.service;

import by.ares.company_service.dto.CompanyDto;

public interface CompanyResourceAssigner {
    CompanyDto assign(Long companyId, Long resourceId);

    CompanyDto unassign(Long companyId, Long resourceId);
}
