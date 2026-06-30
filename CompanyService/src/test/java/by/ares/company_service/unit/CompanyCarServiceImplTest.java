package by.ares.company_service.unit;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.CompanyDto;
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

import static by.ares.company_service.util.TestConstants.EXISTING_CAR_ID;
import static by.ares.company_service.util.TestConstants.EXISTING_COMPANY_ID;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private CacheEvictionService<Company> cacheEvictionService;

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
    void assign() {
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarServiceImpl.assign(EXISTING_COMPANY_ID, EXISTING_CAR_ID);
        assertEquals(companyDto.getCars().size(), result.getCars().size());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void unassign() {
        companyDto.setCars(new HashSet<>());
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(car));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarServiceImpl.unassign(EXISTING_COMPANY_ID, EXISTING_CAR_ID);
        assertEquals(0, result.getCars().size());
        verify(carRepository).findById(EXISTING_CAR_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

}