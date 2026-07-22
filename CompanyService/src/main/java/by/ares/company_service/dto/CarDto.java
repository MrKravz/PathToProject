package by.ares.company_service.dto;

import by.ares.company_service.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String mark;
    private String residentNumber;
    private Integer mileage;
    private CarType carType;
}
