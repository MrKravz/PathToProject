package by.ares.path_list_service.dto.request;

import by.ares.path_list_service.model.CommunicationType;
import by.ares.path_list_service.model.TransportationType;

public record RouteCreationRequest(Integer mileage,
                                   CommunicationType communicationType,
                                   TransportationType transportationType,
                                   String startPoint,
                                   String endPoint) {
}
