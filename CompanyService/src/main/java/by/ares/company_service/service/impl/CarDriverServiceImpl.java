package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.exception.CarDriverNotFoundException;
import by.ares.company_service.mapper.CarDriverDtoMapper;
import by.ares.company_service.mapper.CarDriverRequestMapper;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.service.CarDriverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarDriverServiceImpl implements CarDriverService {

    private final CarDriverRepository carDriverRepository;
    private final CarDriverRequestMapper carDriverRequestMapper;
    private final CarDriverDtoMapper carDriverDtoMapper;
    private final CacheManager cacheManager;

    private static final String COMPANY_CACHE_NAME = "companies";
    private static final String COMPANY_CACHE_KEY = "company:";
    private static final String NOT_FOUND_MESSAGE = "Car driver with this id not found";

    @Override
    @Cacheable(value = "car_drivers", key = "'car_driver:' + #id", sync = true)
    public CarDriverDto findById(Long id) {
        return carDriverRepository.findById(id)
                .map(carDriverDtoMapper::map)
                .orElseThrow(() -> new CarDriverNotFoundException(NOT_FOUND_MESSAGE));
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
                .orElseThrow(() -> new CarDriverNotFoundException(NOT_FOUND_MESSAGE));
        carDriver.setName(updateCarDriverRequest.name())
                .setSurname(updateCarDriverRequest.surname())
                .setLastname(updateCarDriverRequest.lastname());
        carDriver.getCompanies()
                .forEach(company -> {
                            var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
                            if (cache != null) {
                                cache.evict(COMPANY_CACHE_KEY + company.getId());
                            }
                        }
                );
        return carDriverDtoMapper.map(
                carDriverRepository.save(carDriver)
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "car_drivers", key = "'car_driver:' + #id")
    public void deleteById(Long id) {
        var carDriver = carDriverRepository.findById(id)
                .orElseThrow(() -> new CarDriverNotFoundException(NOT_FOUND_MESSAGE));
        carDriver.getCompanies()
                .forEach(company -> {
                            var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
                            if (cache != null) {
                                cache.evict(COMPANY_CACHE_KEY + company.getId());
                            }
                        }
                );
        carDriverRepository.deleteById(id);
    }
}
