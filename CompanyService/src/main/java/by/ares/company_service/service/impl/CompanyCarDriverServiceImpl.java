package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.exception.CompanyNotFoundException;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.CompanyCarDriverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.ares.company_service.util.CompanyServiceConstants.CAR_DRIVER_NOT_FOUND_MESSAGE;
import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class CompanyCarDriverServiceImpl implements CompanyCarDriverService {

    private final CompanyRepository companyRepository;
    private final CarDriverRepository carDriverRepository;
    private final CompanyDtoMapper companyDtoMapper;
    private final CacheEvictionService<Company> cacheEvictionService;

    @Override
    @Transactional
    public CompanyDto assign(Long companyId, Long carDriverId) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
        var carDriver = carDriverRepository.findById(carDriverId)
                .orElseThrow(() -> new CarNotFoundException(CAR_DRIVER_NOT_FOUND_MESSAGE));
        company.addCarDriver(carDriver);
        cacheEvictionService.evict(company);
        return companyDtoMapper.map(companyRepository.save(company));
    }

    @Override
    @Transactional
    public CompanyDto unassign(Long companyId, Long carDriverId) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
        var carDriver = carDriverRepository.findById(carDriverId)
                .orElseThrow(() -> new CarNotFoundException(CAR_DRIVER_NOT_FOUND_MESSAGE));
        company.removeCarDriver(carDriver);
        cacheEvictionService.evict(company);
        return companyDtoMapper.map(companyRepository.save(company));
    }

}
