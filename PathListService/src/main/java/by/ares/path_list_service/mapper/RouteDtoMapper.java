package by.ares.path_list_service.mapper;

import by.ares.path_list_service.dto.RouteDto;
import by.ares.path_list_service.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RouteDtoMapper extends DtoMapper<Route, RouteDto> {
}
