package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.mapper.PathListDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PathListFacadeService implements PathListService {

    private final PathListCoreService coreService;
    private final PathListDtoMapper pathListDtoMapper;
    private final CompanyClient companyClient;
    private final CarClient carClient;
    private final CarDriverClient carDriverClient;

    @Override
    public Page<PathListDto> findAllBySeria(SeriaDto seria, Pageable pageable) {
        Page<PathList> rawPage = coreService.findAllRawBySeria(seria, pageable);
        return enrichPage(rawPage);
    }

    @Override
    public Page<PathListDto> findAllByCreationDateRange(LocalDate start, LocalDate end, Pageable pageable) {
        Page<PathList> rawPage = coreService.findAllRawByDate(start, end, pageable);
        return enrichPage(rawPage);
    }

    @Override
    public PathListDto findById(UUID id) {
        PathList rawPathList = coreService.getRawById(id);
        return enrichSingle(rawPathList);
    }

    @Override
    public PathListDto save(PathListCreationRequest request) {
        PathList savedRaw = coreService.saveRaw(request);
        CarDto carDto = carClient.findById(request.carId());
        CarDriverDto carDriverDto = carDriverClient.findById(request.carDriverId());
        CompanyDto companyDto = companyClient.findById(request.companyId());
        PathListDto finalDto = pathListDtoMapper.map(savedRaw);
        finalDto.setCarDto(carDto);
        finalDto.setCarDriverDto(carDriverDto);
        finalDto.setCompanyDto(companyDto);
        return finalDto;
    }

    private Page<PathListDto> enrichPage(Page<PathList> rawPage) {
        List<Long> companyIds = rawPage.stream()
                .map(PathList::getCompanyId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        Map<Long, CompanyDto> companiesMap = companyIds.isEmpty() ? Map.of() :
                companyClient.findAllById(companyIds).stream()
                        .collect(Collectors.toMap(CompanyDto::getId, company -> company));
        return rawPage.map(raw -> {
            PathListDto dto = pathListDtoMapper.map(raw);
            CompanyDto company = companiesMap.get(raw.getCompanyId());
            assignDto(dto, company, raw.getCarId(), raw.getCarDriverId());
            return dto;
        });
    }

    private PathListDto enrichSingle(PathList raw) {
        PathListDto dto = pathListDtoMapper.map(raw);
        CompanyDto company = companyClient.findById(raw.getCompanyId());
        assignDto(dto, company, raw.getCarId(), raw.getCarDriverId());
        return dto;
    }

    private void assignDto(PathListDto pathListDto, CompanyDto companyDto, Long carId, Long carDriverId) {
        pathListDto.setCompanyDto(companyDto);
        if (companyDto != null) {
            pathListDto.setCarDto(extract(companyDto.getCars(), CarDto::getId, carId));
            pathListDto.setCarDriverDto(extract(companyDto.getCarDrivers(), CarDriverDto::getId, carDriverId));
        }
    }

    private <T, R> T extract(Set<T> set, Function<T, R> idExtractor, R id) {
        if (set == null || id == null) {
            return null;
        }
        return set.stream()
                .filter(x -> Objects.equals(id, idExtractor.apply(x)))
                .findFirst()
                .orElse(null);
    }
}