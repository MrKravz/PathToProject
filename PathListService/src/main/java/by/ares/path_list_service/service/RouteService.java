package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.RouteDto;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.model.Route;

import java.util.UUID;

public interface RouteService {
    RouteDto findById(UUID id);

    Route save(RouteCreationRequest routeCreationRequest);
}
