package by.ares.company_service.mapper;

import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.model.CarDriver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarDriverRequestMapper extends RequestMapper<CarDriver, CarDriverCreationRequest> {
}
