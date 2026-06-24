package by.ares.company_service.service;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;

public interface CarService {

    CarDto findById(Long id);

    CarDto save(CarCreationRequest carCreationRequest);

    CarDto update(UpdateCarRequest updateCarRequest, Long id);

    void deleteById(Long id);

}
