package by.ares.company_service.unit;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.mapper.CarDtoMapper;
import by.ares.company_service.mapper.CarRequestMapper;
import by.ares.company_service.model.Car;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.service.impl.CarServiceImpl;
import by.ares.company_service.util.TestModelsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static by.ares.company_service.util.TestConstants.EXISTING_CAR_ID;
import static by.ares.company_service.util.TestConstants.NOT_EXISTING_CAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarDtoMapper carDtoMapper;
    @Mock
    private CarRequestMapper carRequestMapper;
    @Mock
    private Cache cache;
    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private CarDto carDto;
    private CarCreationRequest carCreationRequest;
    private UpdateCarRequest updateCarRequest;

    @BeforeEach
    void init() {
        car = TestModelsBuilder.buildCar();
        carDto = TestModelsBuilder.buildCarDto();
        carCreationRequest = TestModelsBuilder.buildCarCreationRequest();
        updateCarRequest = TestModelsBuilder.buildUpdateCarRequest();
        lenient().when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Test
    void findById_shouldReturnCar() {
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(carDtoMapper.map(car)).thenReturn(carDto);
        var result = carService.findById(EXISTING_CAR_ID);
        assertEquals(EXISTING_CAR_ID, result.getId());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(carDtoMapper).map(car);
    }

    @Test
    void findById_shouldThrowException() {
        when(carRepository.findById(NOT_EXISTING_CAR_ID)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.findById(NOT_EXISTING_CAR_ID));
        verify(carRepository).findById(NOT_EXISTING_CAR_ID);
    }

    @Test
    void save_shouldSaveCar() {
        when(carRequestMapper.map(carCreationRequest)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoMapper.map(car)).thenReturn(carDto);
        var result = carService.save(carCreationRequest);
        assertEquals(carDto, result);
        verify(carRequestMapper).map(carCreationRequest);
        verify(carRepository).save(car);
        verify(carDtoMapper).map(car);
    }

    @Test
    void update_shouldUpdateCar() {
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoMapper.map(car)).thenReturn(carDto);
        var result = carService.update(updateCarRequest, EXISTING_CAR_ID);
        assertEquals(carDto.getMark(), result.getMark());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(carRepository).save(car);
        verify(carDtoMapper).map(car);
    }

    @Test
    void update_shouldThrowException() {
        when(carRepository.findById(NOT_EXISTING_CAR_ID)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class,
                () -> carService.update(updateCarRequest, NOT_EXISTING_CAR_ID));
        verify(carRepository).findById(NOT_EXISTING_CAR_ID);
    }

    @Test
    void deleteById_shouldDeleteCar() {
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        carService.deleteById(EXISTING_CAR_ID);
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(carRepository).deleteById(EXISTING_CAR_ID);
    }

    @Test
    void deleteById_shouldThrowException() {
        when(carRepository.findById(NOT_EXISTING_CAR_ID)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.deleteById(NOT_EXISTING_CAR_ID));
        verify(carRepository).findById(NOT_EXISTING_CAR_ID);
    }
}