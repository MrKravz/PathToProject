package by.ares.company_service.dto.request;

import by.ares.company_service.model.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CarCreationRequest(
        @NotBlank(message = "Car mark cannot be blank")
        @Size(max = 100, message = "Car mark must not exceed 100 characters")
        String mark,

        @NotBlank(message = "Resident number cannot be blank")
        @Size(max = 20, message = "Resident number must not exceed 20 characters")
        String residentNumber,

        @NotNull(message = "Mileage must be provided")
        @PositiveOrZero(message = "Mileage cannot be negative")
        Integer mileage,

        @NotNull(message = "Car type must be provided")
        CarType carType
) {}
