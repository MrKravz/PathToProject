package by.ares.company_service.dto.request;

import by.ares.company_service.model.CarType;

import jakarta.validation.constraints.*;

public record UpdateCarRequest(
        @NotBlank(message = "Car carName cannot be blank")
        @Size(max = 100, message = "Car carName must not exceed 100 characters")
        String carName,

        @Positive(message = "Fuel consumption cannot be negative")
        Integer fuelConsumption,

        @Positive(message = "Action fuel consumption cannot be negative")
        Integer actionFuelConsumption,

        @Positive(message = "Oil consumption cannot be negative")
        Integer oilConsumption,

        @NotNull(message = "Car type must be provided")
        CarType carType
) {}
