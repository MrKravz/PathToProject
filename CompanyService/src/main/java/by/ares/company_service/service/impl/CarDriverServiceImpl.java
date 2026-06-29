package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.exception.CarDriverNotFoundException;
import by.ares.company_service.mapper.CarDriverDtoMapper;
import by.ares.company_service.mapper.CarDriverRequestMapper;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.CarDriverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static by.ares.company_service.util.CompanyServiceConstants.*;

@Service
@RequiredArgsConstructor
public class CarDriverServiceImpl implements CarDriverService {

    private final CarDriverRepository carDriverRepository;
    private final CarDriverRequestMapper carDriverRequestMapper;
    private final CarDriverDtoMapper carDriverDtoMapper;
    private final CacheEvictionService<Company> cacheEvictionService;

    @Override
    @Cacheable(value = "car_drivers", key = "'car_driver:' + #id", sync = true)
    public CarDriverDto findById(Long id) {
        return carDriverRepository.findById(id)
                .map(carDriverDtoMapper::map)
                .orElseThrow(() -> new CarDriverNotFoundException(CAR_DRIVER_NOT_FOUND_MESSAGE));
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
    @CachePut(value = "car_drivers", key = "'car_driver:' + #id")
    public CarDriverDto update(UpdateCarDriverRequest updateCarDriverRequest, Long id) {
        var carDriver = carDriverRepository.findById(id)
                .orElseThrow(() -> new CarDriverNotFoundException(CAR_DRIVER_NOT_FOUND_MESSAGE));
        carDriver.setName(updateCarDriverRequest.name())
                .setSurname(updateCarDriverRequest.surname())
                .setLastname(updateCarDriverRequest.lastname());
        cacheEvictionService.evictAll(carDriver.getCompanies());
        return carDriverDtoMapper.map(
                carDriverRepository.save(carDriver)
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "car_drivers", key = "'car_driver:' + #id")
    public void deleteById(Long id) {
        var carDriver = carDriverRepository.findById(id)
                .orElseThrow(() -> new CarDriverNotFoundException(CAR_DRIVER_NOT_FOUND_MESSAGE));
        cacheEvictionService.evictAll(carDriver.getCompanies());
        carDriverRepository.deleteById(id);
    }
}
