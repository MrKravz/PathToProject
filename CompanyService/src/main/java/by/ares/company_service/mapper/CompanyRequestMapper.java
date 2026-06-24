package by.ares.company_service.mapper;

import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.model.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyRequestMapper extends RequestMapper<Company, CompanyCreationRequest> {
}
