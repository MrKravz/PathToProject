package by.ares.company_service.unit;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;
import by.ares.company_service.exception.CompanyNotFoundException;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.mapper.CompanyRequestMapper;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.ares.company_service.util.TestConstants.EXISTING_COMPANY_ID;
import static by.ares.company_service.util.TestConstants.NOT_EXISTING_COMPANY_ID;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyDtoMapper companyDtoMapper;
    @Mock
    private CompanyRequestMapper companyRequestMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;
    private CompanyDto companyDto;
    private CompanyCreationRequest companyCreationRequest;
    private UpdateCompanyRequest updateCompanyRequest;

    @BeforeEach
    void init() {
        company = buildCompany();
        companyDto = buildCompanyDto();
        companyCreationRequest = buildCompanyCreationRequest();
        updateCompanyRequest = buildUpdateCompanyRequest();
    }

    @Test
    void findAllById_shouldReturnCompanyList() {
        when(companyRepository.findAllById(List.of(EXISTING_COMPANY_ID)))
                .thenReturn(List.of(company));
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyService.findAllById(List.of(EXISTING_COMPANY_ID));
        assertTrue(result.contains(companyDto));
        assertEquals(1, (long) result.size());
        verify(companyRepository).findAllById(List.of(EXISTING_COMPANY_ID));
        verify(companyDtoMapper).map(company);
    }

    @Test
    void findById_shouldReturnCompany() {
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyService.findById(EXISTING_COMPANY_ID);
        assertEquals(EXISTING_COMPANY_ID, result.getId());
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void findById_shouldThrowException() {
        when(companyRepository.findById(NOT_EXISTING_COMPANY_ID)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(NOT_EXISTING_COMPANY_ID));
        verify(companyRepository).findById(NOT_EXISTING_COMPANY_ID);
    }

    @Test
    void save_shouldSaveCompany() {
        when(companyRequestMapper.map(companyCreationRequest)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyService.save(companyCreationRequest);
        assertEquals(companyDto, result);
        verify(companyRequestMapper).map(companyCreationRequest);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void update_shouldUpdateCompany() {
        when(companyRepository.findById(EXISTING_COMPANY_ID)).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyDtoMapper.map(company)).thenReturn(companyDto);
        var result = companyService.update(updateCompanyRequest, EXISTING_COMPANY_ID);
        assertEquals(companyDto.getCompanyName(), result.getCompanyName());
        verify(companyRepository).findById(EXISTING_COMPANY_ID);
        verify(companyRepository).save(company);
        verify(companyDtoMapper).map(company);
    }

    @Test
    void update_shouldThrowException() {
        when(companyRepository.findById(NOT_EXISTING_COMPANY_ID)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class,
                () -> companyService.update(updateCompanyRequest, NOT_EXISTING_COMPANY_ID));
        verify(companyRepository).findById(NOT_EXISTING_COMPANY_ID);
    }

    @Test
    void deleteById_shouldDeleteCompany() {
        companyService.deleteById(EXISTING_COMPANY_ID);
        verify(companyRepository).deleteById(EXISTING_COMPANY_ID);
    }
}