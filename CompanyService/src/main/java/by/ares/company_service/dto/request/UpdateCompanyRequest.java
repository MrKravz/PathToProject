package by.ares.company_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCompanyRequest(
        @NotBlank(message = "Company name cannot be blank")
        @Size(max = 100, message = "Company name must not exceed 100 characters")
        String companyName,
        @NotBlank(message = "Address cannot be blank")
        @Size(max = 255, message = "Address must not exceed 255 characters")
        String address,
        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
                message = "Invalid phone number format (expected international format, e.g., +1234567890)")
        String phoneNumber
) {}
