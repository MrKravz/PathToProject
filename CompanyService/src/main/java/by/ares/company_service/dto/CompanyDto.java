package by.ares.company_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
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
