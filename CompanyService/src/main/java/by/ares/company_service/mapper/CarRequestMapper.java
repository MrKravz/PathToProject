package by.ares.company_service.mapper;

import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarRequestMapper extends RequestMapper<Car, CarCreationRequest> {
}
