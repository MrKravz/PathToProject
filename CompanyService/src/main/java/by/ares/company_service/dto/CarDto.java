package by.ares.company_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private Long id;
    private String mark;
    private String residentNumber;
    private Integer mileage;
}
