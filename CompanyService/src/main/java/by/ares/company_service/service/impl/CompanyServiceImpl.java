package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;
import by.ares.company_service.exception.CompanyNotFoundException;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.mapper.CompanyRequestMapper;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyDtoMapper companyDtoMapper;
    private final CompanyRequestMapper companyRequestMapper;


    @Override
    public List<CompanyDto> findAllById(List<Long> ids) {
        return companyRepository.findAllById(ids)
                .stream()
                .map(companyDtoMapper::map)
                .toList();
    }

    @Override
    @Cacheable(value = "companies", key = "'company:' + #id", sync = true)
    public CompanyDto findById(Long id) {
        return companyRepository.findById(id)
                .map(companyDtoMapper::map)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
    }

    @Override
    public CompanyDto save(CompanyCreationRequest companyCreationRequest) {
        return companyDtoMapper.map(
                companyRepository.save(companyRequestMapper.map(companyCreationRequest))
        );
    }

    @Override
    @CachePut(value = "companies", key = "'company:' + #id")
    public CompanyDto update(UpdateCompanyRequest updateCompanyRequest, Long id) {
        var company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
        company.setCompanyName(updateCompanyRequest.companyName())
                .setFullName(updateCompanyRequest.fullName())
                .setAddress(updateCompanyRequest.address())
                .setPhoneNumber(updateCompanyRequest.phoneNumber());
        return companyDtoMapper.map(
                companyRepository.save(company)
        );
    }

    @Override
    @CacheEvict(value = "companies", key = "'company:' + #id")
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
