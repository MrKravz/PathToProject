package by.ares.document_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDriverDto {
    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String driverLicenseNumber;
}
