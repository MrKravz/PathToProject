package by.ares.company_service.mapper;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.model.Company;
import org.mapstruct.Mapper;

@Mapper(uses = {CarDriverDtoMapper.class, CarDtoMapper.class})
public interface CompanyDtoMapper extends DtoMapper<Company, CompanyDto>{
}
