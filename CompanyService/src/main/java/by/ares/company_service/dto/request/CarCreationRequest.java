package by.ares.company_service.dto.request;

import by.ares.company_service.model.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CarCreationRequest(
        @NotBlank(message = "Car car name cannot be blank")
        @Size(max = 100, message = "Car car name must not exceed 100 characters")
        String carName,

        @NotBlank(message = "Resident number cannot be blank")
        @Size(max = 20, message = "Resident number must not exceed 20 characters")
        String residentNumber,

        @Positive(message = "Fuel consumption cannot be negative")
        Float fuelConsumption,

        @Positive(message = "Action fuel consumption cannot be negative")
        Float actionFuelConsumption,

        @Positive(message = "Oil consumption cannot be negative")
        Float oilConsumption,

        @NotNull(message = "Car type must be provided")
        CarType carType
) {}
