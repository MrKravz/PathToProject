package by.ares.company_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCarDriverRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        @Size(max = 50, message = "Surname must not exceed 50 characters")
        String surname,

        @Size(max = 50, message = "Lastname must not exceed 50 characters")
        String lastname
) {}
