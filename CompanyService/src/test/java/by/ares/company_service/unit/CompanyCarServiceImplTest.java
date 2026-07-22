package by.ares.company_service.unit;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.exception.CompanyNotFoundException;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.model.Car;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.impl.CompanyCarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static by.ares.company_service.util.TestConstants.*;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyCarServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CompanyDtoMapper companyDtoMapper;
    @Mock
    private CacheEvictionService<Long> cacheEvictionService;

    @InjectMocks
    private CompanyCarServiceImpl companyCarServiceImpl;

    private Car car;
    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    void init() {
        car = buildCar();
        CarDto carDto = buildCarDto();
        company = buildCompany();
        companyDto = buildCompanyDto();
        companyDto.setCars(Set.of(carDto));
    }

    @Test
    void assign_shouldReturnCompany() {
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarServiceImpl.assign(EXISTING_COMPANY_ID, EXISTING_CAR_ID);
        assertEquals(companyDto.getCars().size(), result.getCars().size());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company.getId());
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void assign_shouldThrowCompanyException() {
        when(companyRepository.findById(NOT_EXISTING_COMPANY_ID)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () ->
                companyCarServiceImpl.assign(NOT_EXISTING_COMPANY_ID, EXISTING_CAR_DRIVER_ID));
        verify(companyRepository).findById(NOT_EXISTING_COMPANY_ID);
    }

    @Test
    void assign_shouldThrowCarDriverException() {
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(carRepository.findById(NOT_EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () ->
                companyCarServiceImpl.assign(EXISTING_COMPANY_ID, NOT_EXISTING_CAR_DRIVER_ID));
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(carRepository).findById(NOT_EXISTING_CAR_DRIVER_ID);
    }

    @Test
    void unassign_shouldReturnCompany() {
        companyDto.setCars(new HashSet<>());
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarServiceImpl.unassign(EXISTING_COMPANY_ID, EXISTING_CAR_ID);
        assertEquals(0, result.getCars().size());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company.getId());
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void unassign_shouldThrowCompanyException() {
        when(companyRepository.findById(NOT_EXISTING_COMPANY_ID)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () ->
                companyCarServiceImpl.unassign(NOT_EXISTING_COMPANY_ID, EXISTING_CAR_DRIVER_ID));
        verify(companyRepository).findById(NOT_EXISTING_COMPANY_ID);
    }

    @Test
    void unassign_shouldThrowCarDriverException() {
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(carRepository.findById(NOT_EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () ->
                companyCarServiceImpl.unassign(EXISTING_COMPANY_ID, NOT_EXISTING_CAR_DRIVER_ID));
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(carRepository).findById(NOT_EXISTING_CAR_DRIVER_ID);
    }

}