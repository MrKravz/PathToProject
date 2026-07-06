package by.ares.path_list_service.dto;

import by.ares.path_list_service.model.TransportationType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RouteDto {
    private UUID id;
    private TransportationType transportationType;
    private String startPoint;
    private String endPoint;
}
