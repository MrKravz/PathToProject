package by.ares.path_list_service.dto.request;

import by.ares.path_list_service.dto.SeriaDto;

public record PathListCreationRequest(Integer number, Long carId, Long carDriverId,
                                      Long companyId, RouteCreationRequest routeCreationRequest,
                                      SeriaDto seriaDto) {
}
