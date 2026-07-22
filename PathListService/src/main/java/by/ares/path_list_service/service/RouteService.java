package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.model.Route;

public interface RouteService {
    Route save(RouteCreationRequest routeCreationRequest);
}
