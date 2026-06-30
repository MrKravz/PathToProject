package by.ares.company_service.unit;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.model.CarDriver;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.impl.CompanyCarDriverServiceImpl;
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
import static by.ares.company_service.util.TestModelsBuilder.buildCompanyDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyCarDriverServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CarDriverRepository carDriverRepository;
    @Mock
    private CompanyDtoMapper companyDtoMapper;
    @Mock
    private CacheEvictionService<Company> cacheEvictionService;

    @InjectMocks
    private CompanyCarDriverServiceImpl companyCarDriverService;

    private CarDriver carDriver;
    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    void init() {
        carDriver = buildCarDriver();
        CarDriverDto carDriverDto = buildCarDriverDto();
        company = buildCompany();
        companyDto = buildCompanyDto();
        companyDto.setCarDrivers(Set.of(carDriverDto));
    }

    @Test
    void assign() {
        when(carDriverRepository.findById(EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.of(carDriver));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarDriverService.assign(EXISTING_COMPANY_ID, EXISTING_CAR_DRIVER_ID);
        assertEquals(companyDto.getCars().size(), result.getCars().size());
        verify(carDriverRepository).findById(EXISTING_CAR_DRIVER_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void unassign() {
        companyDto.setCarDrivers(new HashSet<>());
        when(carDriverRepository.findById(EXISTING_CAR_DRIVER_ID)).thenReturn(Optional.of(carDriver));
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyCarDriverService.unassign(EXISTING_COMPANY_ID, EXISTING_CAR_DRIVER_ID);
        assertEquals(0, result.getCars().size());
        verify(carDriverRepository).findById(EXISTING_CAR_DRIVER_ID);
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(cacheEvictionService).evict(company);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

}