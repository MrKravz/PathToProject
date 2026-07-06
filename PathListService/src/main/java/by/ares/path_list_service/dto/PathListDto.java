package by.ares.path_list_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PathListDto {
    private UUID id;
    private Integer number;
    private CarDto carDto;
    private CarDriverDto carDriverDto;
    private CompanyDto companyDto;
    private LocalDate reclamationDate;
    private RouteDto routeDto;
    private SeriaDto seriaDto;
}
