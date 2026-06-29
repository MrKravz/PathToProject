package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.mapper.CarDtoMapper;
import by.ares.company_service.mapper.CarRequestMapper;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.service.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static by.ares.company_service.util.CompanyServiceConstants.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarDtoMapper carDtoMapper;
    private final CarRequestMapper carRequestMapper;
    private final CacheManager cacheManager;

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
        car.setMark(updateCarRequest.mark())
                .setMileage(updateCarRequest.mileage());
        car.getCompanies()
                .forEach(company -> {
                            var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
                            if (cache != null) {
                                cache.evict(COMPANY_CACHE_KEY + company.getId());
                            }
                        }
                );
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
        car.getCompanies()
                .forEach(company -> {
                            var cache = cacheManager.getCache(COMPANY_CACHE_NAME);
                            if (cache != null) {
                                cache.evict(COMPANY_CACHE_KEY + company.getId());
                            }
                        }
                );
        carRepository.deleteById(id);
    }

}
