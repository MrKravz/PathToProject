package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.exception.PathListNotFoundException;
import by.ares.path_list_service.mapper.PathListDtoMapper;
import by.ares.path_list_service.mapper.PathListRequestMapper;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.service.PathListService;
import by.ares.path_list_service.service.RouteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.ares.path_list_service.util.PathListServiceConstants.PATH_LIST_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class PathListServiceImpl implements PathListService {

    private final PathListRepository pathListRepository;
    private final PathListDtoMapper pathListDtoMapper;
    private final PathListRequestMapper pathListRequestMapper;
    private final SeriaDtoMapper seriaDtoMapper;
    private final CompanyClient companyClient;
    private final CarClient carClient;
    private final CarDriverClient carDriverClient;
    private final RouteService routeService;

    @Override
    public Page<PathListDto> findAllBySeria(SeriaDto seria, Pageable pageable) {
        var pathLists = pathListRepository.findAllBySeria(seriaDtoMapper.remap(seria), pageable);
        return pathLists.map(pathList -> {
            PathListDto dto = pathListDtoMapper.map(pathList);
            CompanyDto company = findCompanies(pathLists.stream()).get(pathList.getCompanyId());
            assignDto(dto, company, pathList.getCarId(), pathList.getCarDriverId());
            return dto;
        });
    }

    @Override
    public Page<PathListDto> findAllByCreationDateRange(LocalDate start, LocalDate end,
                                                        Pageable pageable) {
        var pathLists = pathListRepository.findAllByReclamationDateBetween(start, end, pageable);
        return pathLists.map(pathList -> {
            PathListDto dto = pathListDtoMapper.map(pathList);
            CompanyDto company = findCompanies(pathLists.stream()).get(pathList.getCompanyId());
            assignDto(dto, company, pathList.getCarId(), pathList.getCarDriverId());
            return dto;
        });
    }

    @Override
    public PathListDto findById(UUID id) {
        return pathListRepository.findById(id)
                .map(pathListDtoMapper::map)
                .orElseThrow(() -> new PathListNotFoundException(PATH_LIST_NOT_FOUND_MESSAGE));
    }

    @Override
    @Transactional
    public PathListDto save(PathListCreationRequest pathListCreationRequest) {
        var carDto = carClient.findById(pathListCreationRequest.carId());
        var carDriverDto = carDriverClient.findById(pathListCreationRequest.carDriverId());
        var companyDto = companyClient.findById(pathListCreationRequest.companyId());
        var route = routeService.save(pathListCreationRequest.routeCreationRequest());
        var pathList = pathListRequestMapper.map(pathListCreationRequest);
        pathList.setRoute(route);
        var pathListDto =  pathListDtoMapper.map(pathListRepository.save(pathList));
        pathListDto.setCompanyDto(companyDto);
        pathListDto.setCarDto(carDto);
        pathListDto.setCarDriverDto(carDriverDto);
        return pathListDto;
    }

    private Map<Long, CompanyDto> findCompanies(Stream<PathList> pathLists) {
        List<Long> companyIds = pathLists
                .map(PathList::getCompanyId)
                .distinct()
                .toList();
        return companyClient.findAllById(companyIds)
                .stream()
                .collect(Collectors.toMap(CompanyDto::getId, company -> company));
    }

    private void assignDto(PathListDto pathListDto, CompanyDto companyDto,
                           Long carId, Long carDriverId) {
        pathListDto.setCompanyDto(companyDto);
        if (companyDto != null) {
            pathListDto.setCarDto(extract(companyDto.getCars(), CarDto::getId, carId));
            pathListDto.setCarDriverDto(extract(companyDto.getCarDrivers(), CarDriverDto::getId,
                    carDriverId));
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
