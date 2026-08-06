package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.mapper.CarDtoMapper;
import by.ares.company_service.mapper.CarRequestMapper;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static by.ares.company_service.util.CompanyServiceConstants.CAR_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarDtoMapper carDtoMapper;
    private final CarRequestMapper carRequestMapper;
    private final CacheEvictionService<Long> cacheEvictionService;

    @Override
    @Cacheable(value = "cars", key = "'car:' + #id", sync = true)
    public CarDto findById(Long id) {
        return carRepository.findById(id)
                .map(carDtoMapper::map)
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND_MESSAGE));
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
    @CachePut(value = "cars", key = "'car:' + #id")
    public CarDto update(UpdateCarRequest updateCarRequest, Long id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND_MESSAGE));
        car.setCarName(updateCarRequest.mark())
                .setMileage(updateCarRequest.mileage());
        cacheEvictionService.evictAll(car.getCompanies()
                .stream()
                .map(Company::getId)
                .collect(Collectors.toSet()));
        return carDtoMapper.map(
                carRepository.save(car)
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "cars", key = "'car:' + #id")
    public void deleteById(Long id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND_MESSAGE));
        cacheEvictionService.evictAll(car.getCompanies()
                .stream()
                .map(Company::getId)
                .collect(Collectors.toSet()));
        carRepository.deleteById(id);
    }

}
