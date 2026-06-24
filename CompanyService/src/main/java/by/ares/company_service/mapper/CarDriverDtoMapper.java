package by.ares.company_service.mapper;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.model.CarDriver;
import org.mapstruct.Mapper;

@Mapper
public interface CarDriverDtoMapper extends DtoMapper<CarDriver, CarDriverDto>{
}
