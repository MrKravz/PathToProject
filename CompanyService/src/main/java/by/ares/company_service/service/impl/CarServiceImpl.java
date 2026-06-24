package by.ares.company_service.service.impl;

import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.mapper.CarDtoMapper;
import by.ares.company_service.mapper.CarRequestMapper;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.service.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarDtoMapper carDtoMapper;
    private final CarRequestMapper carRequestMapper;

    @Override
    public CarDto findById(Long id) {
        return carRepository.findById(id)
                .map(carDtoMapper::map)
                .orElseThrow(() -> new CarNotFoundException("Car with this id not found"));
    }

    @Override
    @Transactional
    public CarDto save(CarCreationRequest carCreationRequest) {
        return carDtoMapper.map(
                carRepository.save(carRequestMapper.map(carCreationRequest))
        );
    }

    @Override
    @Transactional
    public CarDto update(UpdateCarRequest updateCarRequest, Long id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with this id not found"));
        car.setMark(updateCarRequest.mark())
                .setMileage(updateCarRequest.mileage());
        return carDtoMapper.map(
                carRepository.save(car)
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

}
