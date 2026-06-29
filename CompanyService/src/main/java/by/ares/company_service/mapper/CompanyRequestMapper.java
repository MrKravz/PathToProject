package by.ares.company_service.mapper;

import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyRequestMapper extends RequestMapper<Company, CompanyCreationRequest> {
}
