package by.ares.company_service.mapper;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CarDriverDtoMapper.class, CarDtoMapper.class})
public interface CompanyDtoMapper extends DtoMapper<Company, CompanyDto>{
}
