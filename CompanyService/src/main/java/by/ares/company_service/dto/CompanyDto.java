package by.ares.company_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long id;
    private String identifier;
    private String companyName;
    private String fullName;
    private String address;
    private String phoneNumber;
    private Set<CarDto> cars;
    private Set<CarDriverDto> carDrivers;
}
