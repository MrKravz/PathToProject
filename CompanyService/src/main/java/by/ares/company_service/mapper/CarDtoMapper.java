package by.ares.company_service.mapper;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarDtoMapper extends DtoMapper<Car, CarDto> {
}
