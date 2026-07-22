package by.ares.company_service.service;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> findAllById(List<Long> ids);

    CompanyDto findById(Long id);

    CompanyDto save(CompanyCreationRequest companyCreationRequest);

    CompanyDto update(UpdateCompanyRequest updateCompanyRequest, Long id);

    void deleteById(Long id);

}

