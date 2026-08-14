package by.ares.document_service.dto;

import by.ares.document_service.model.DocumentForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathListEventDto {
    private UUID id;
    private Integer number;
    private Long carId;
    private Long carDriverId;
    private Long companyId;
    private LocalDate reclamationDate;
    private LocalDate expirationDate;
    private DocumentForm documentForm;
    private RouteDto route;
    private SeriaDto seria;
}
