package by.ares.document_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String carName;
    private String residentNumber;
    private Float fuelConsumption;
    private Float actionFuelConsumption;
    private Float oilConsumption;
    private CarType carType;

    enum CarType {
        CAR, TRUCK, BUS, ARMY_VEHICLE
    }
}
