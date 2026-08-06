package by.ares.path_list_service.dto;

import by.ares.path_list_service.model.CommunicationType;
import by.ares.path_list_service.model.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
    private UUID id;
    private Integer mileage;
    private CommunicationType communicationType;
    private TransportationType transportationType;
    private String startPoint;
    private String endPoint;
}
