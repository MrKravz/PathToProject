package by.ares.path_list_service.dto;

import by.ares.path_list_service.model.TransportationType;

public record RouteCreationRequest(TransportationType transportationType, String startPoint,
                                   String endPoint) {
}
