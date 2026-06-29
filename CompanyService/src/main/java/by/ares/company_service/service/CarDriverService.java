package by.ares.company_service.service;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;

public interface CarDriverService {

    CarDriverDto findById(Long id);

    CarDriverDto save(CarDriverCreationRequest carDriverCreationRequest);

    CarDriverDto update(UpdateCarDriverRequest updateCarDriverRequest, Long id);

    void deleteById(Long id);

}
