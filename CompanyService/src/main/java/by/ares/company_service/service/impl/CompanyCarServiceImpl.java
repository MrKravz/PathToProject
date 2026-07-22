package by.ares.company_service.service.impl;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.exception.CarNotFoundException;
import by.ares.company_service.exception.CompanyNotFoundException;
import by.ares.company_service.mapper.CompanyDtoMapper;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.repository.CompanyRepository;
import by.ares.company_service.service.CacheEvictionService;
import by.ares.company_service.service.CompanyCarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.ares.company_service.util.CompanyServiceConstants.CAR_NOT_FOUND_MESSAGE;
import static by.ares.company_service.util.CompanyServiceConstants.COMPANY_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class CompanyCarServiceImpl implements CompanyCarService {

    private final CompanyRepository companyRepository;
    private final CarRepository carRepository;
    private final CompanyDtoMapper companyDtoMapper;
    private final CacheEvictionService<Long> cacheEvictionService;

    @Override
    @Transactional
    public CompanyDto assign(Long companyId, Long carId) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
        var car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND_MESSAGE));
        company.addCar(car);
        cacheEvictionService.evict(company.getId());
        return companyDtoMapper.map(companyRepository.save(company));
    }

    @Override
    @Transactional
    public CompanyDto unassign(Long companyId, Long carId) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND_MESSAGE));
        var car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND_MESSAGE));
        company.removeCar(car);
        cacheEvictionService.evict(company.getId());
        return companyDtoMapper.map(companyRepository.save(company));
    }

}
