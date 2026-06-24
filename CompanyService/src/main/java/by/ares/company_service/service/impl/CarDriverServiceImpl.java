package by.ares.company_service.service.impl;

import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.exception.CarDriverNotFoundException;
import by.ares.company_service.mapper.CarDriverDtoMapper;
import by.ares.company_service.mapper.CarDriverRequestMapper;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.service.CarDriverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDriverServiceImpl implements CarDriverService {

    private final CarDriverRepository carDriverRepository;
    private final CarDriverRequestMapper carDriverRequestMapper;
    private final CarDriverDtoMapper carDriverDtoMapper;

    @Override
    public CarDriverDto findById(Long id) {
        return carDriverRepository.findById(id)
                .map(carDriverDtoMapper::map)
                .orElseThrow(() -> new CarDriverNotFoundException("Car driver with this id not found"));
    }

    @Override
    @Transactional
    public CarDriverDto save(CarDriverCreationRequest carDriverCreationRequest) {
        return carDriverDtoMapper.map(
                carDriverRepository.save(carDriverRequestMapper.map(carDriverCreationRequest))
        );
    }

    @Override
    @Transactional
    public CarDriverDto update(UpdateCarDriverRequest updateCarDriverRequest, Long id) {
        var carDriver = carDriverRepository.findById(id)
                .orElseThrow(() -> new CarDriverNotFoundException("Car driver with this id not found"));
        carDriver.setName(updateCarDriverRequest.name())
                .setSurname(updateCarDriverRequest.surname())
                .setLastname(updateCarDriverRequest.lastname());
        return carDriverDtoMapper.map(
                carDriverRepository.save(carDriver)
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        carDriverRepository.deleteById(id);
    }
}
