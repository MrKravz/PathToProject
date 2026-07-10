package by.ares.path_list_service.mapper;

import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RouteRequestMapper {
    Route map(RouteCreationRequest routeCreationRequest);
}
