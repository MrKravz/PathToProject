package by.ares.company_service.service;

import by.ares.company_service.dto.CompanyDto;

public interface CompanyCarService extends CompanyResourceAssigner {

    CompanyDto assign(Long companyId, Long carId);

    CompanyDto unassign(Long companyId, Long carId);

}
