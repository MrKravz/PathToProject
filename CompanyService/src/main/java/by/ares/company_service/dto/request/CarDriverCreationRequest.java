package by.ares.company_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CarDriverCreationRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        @Size(max = 50, message = "Surname must not exceed 50 characters")
        String surname,

        @Size(max = 50, message = "Lastname must not exceed 50 characters")
        String lastname,

        @NotBlank(message = "Driver license number cannot be blank")
        @Size(max = 30, message = "Driver license number must not exceed 30 characters")
        String driverLicenseNumber
) {}
