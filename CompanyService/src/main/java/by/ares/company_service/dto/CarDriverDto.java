package by.ares.company_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDriverDto {
    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String driverLicenseNumber;
}
