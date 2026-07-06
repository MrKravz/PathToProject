package by.ares.path_list_service.dto;

public record PathListCreationRequest(Integer number, Long carId, Long carDriverId,
                                      Long companyId, RouteDto routeDto, SeriaDto seriaDto) {
}
