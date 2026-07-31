package by.ares.document_service.dto;

import by.ares.document_service.model.TransportationType;
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
    private TransportationType transportationType;
    private String startPoint;
    private String endPoint;
}
