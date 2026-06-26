package by.ares.company_service.unit;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.exception.CarDriverNotFoundException;
import by.ares.company_service.mapper.CarDriverDtoMapper;
import by.ares.company_service.mapper.CarDriverRequestMapper;
import by.ares.company_service.model.CarDriver;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.service.impl.CarDriverServiceImpl;
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

import static by.ares.company_service.util.TestConstants.EXISTING_CAR_DRIVER_ID;
import static by.ares.company_service.util.TestConstants.NOT_EXISTING_CAR_DRIVER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarDriverServiceImplTest {

    @Mock
    private CarDriverRepository carDriverRepository;
    @Mock
    private CarDriverRequestMapper carDriverRequestMapper;
    @Mock
    private CarDriverDtoMapper carDriverDtoMapper;
    @Mock
    private Cache cache;
    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CarDriverServiceImpl carDriverService;

    private CarDriver carDriver;
    private CarDriverDto carDriverDto;
    private CarDriverCreationRequest carDriverCreationRequest;
    private UpdateCarDriverRequest updateCarDriverRequest;

    @BeforeEach
    void init() {
        carDriver = TestModelsBuilder.buildCarDriver();
        carDriverDto = TestModelsBuilder.buildCarDriverDto();
        carDriverCreationRequest = TestModelsBuilder.buildCarDriverCreationRequest();
        updateCarDriverRequest = TestModelsBuilder.buildUpdateCarDriverRequest();
        lenient().when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Test
    void findById_shouldReturnCarDriver() {
        when(carDriverRepository.findById(EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.of(carDriver));
        when(carDriverDtoMapper.map(carDriver)).thenReturn(carDriverDto);
        var result = carDriverService.findById(EXISTING_CAR_DRIVER_ID);
        assertEquals(EXISTING_CAR_DRIVER_ID, result.getId());
        verify(carDriverRepository).findById(EXISTING_CAR_DRIVER_ID);
        verify(carDriverDtoMapper).map(carDriver);
    }

    @Test
    void findById_shouldThrowException() {
        when(carDriverRepository.findById(NOT_EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.empty());
        assertThrows(CarDriverNotFoundException.class, () -> carDriverService.findById(NOT_EXISTING_CAR_DRIVER_ID));
        verify(carDriverRepository).findById(NOT_EXISTING_CAR_DRIVER_ID);
    }

    @Test
    void save_shouldSaveCarDriver() {
        when(carDriverRequestMapper.map(carDriverCreationRequest)).thenReturn(carDriver);
        when(carDriverRepository.save(carDriver)).thenReturn(carDriver);
        when(carDriverDtoMapper.map(carDriver)).thenReturn(carDriverDto);
        var result = carDriverService.save(carDriverCreationRequest);
        assertEquals(carDriverDto, result);
        verify(carDriverRequestMapper).map(carDriverCreationRequest);
        verify(carDriverRepository).save(carDriver);
        verify(carDriverDtoMapper).map(carDriver);
    }

    @Test
    void update_shouldUpdateCarDriver() {
        when(carDriverRepository.findById(EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.of(carDriver));
        when(carDriverRepository.save(carDriver)).thenReturn(carDriver);
        when(carDriverDtoMapper.map(carDriver)).thenReturn(carDriverDto);
        var result = carDriverService.update(updateCarDriverRequest, EXISTING_CAR_DRIVER_ID);
        assertEquals(carDriverDto.getLastname(), result.getLastname());
        verify(carDriverRepository).findById(EXISTING_CAR_DRIVER_ID);
        verify(carDriverRepository).save(carDriver);
        verify(carDriverDtoMapper).map(carDriver);
    }

    @Test
    void update_shouldThrowException() {
        when(carDriverRepository.findById(NOT_EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.empty());
        assertThrows(CarDriverNotFoundException.class,
                () -> carDriverService.update(updateCarDriverRequest, NOT_EXISTING_CAR_DRIVER_ID));
        verify(carDriverRepository).findById(NOT_EXISTING_CAR_DRIVER_ID);
    }

    @Test
    void deleteById_shouldDeleteCarDriver() {
        when(carDriverRepository.findById(EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.of(carDriver));
        carDriverService.deleteById(EXISTING_CAR_DRIVER_ID);
        verify(carDriverRepository).findById(EXISTING_CAR_DRIVER_ID);
    }

    @Test
    void deleteById_shouldThrowException() {
        when(carDriverRepository.findById(NOT_EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.empty());
        assertThrows(CarDriverNotFoundException.class,
                () -> carDriverService.deleteById(NOT_EXISTING_CAR_DRIVER_ID));
        verify(carDriverRepository).findById(NOT_EXISTING_CAR_DRIVER_ID);
    }

}