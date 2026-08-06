package by.ares.company_service.dto.request;

import by.ares.company_service.model.CarType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateCarRequest(
        @NotBlank(message = "Car carName cannot be blank")
        @Size(max = 100, message = "Car carName must not exceed 100 characters")
        String mark,

        @NotNull(message = "Mileage must be provided")
        @PositiveOrZero(message = "Mileage cannot be negative")
        Integer mileage,

        @NotNull(message = "Car type must be provided")
        CarType carType
) {}
