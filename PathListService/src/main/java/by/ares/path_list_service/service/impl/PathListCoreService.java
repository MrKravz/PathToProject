package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.exception.PathListNotFoundException;
import by.ares.path_list_service.exception.SeriaNotFoundException;
import by.ares.path_list_service.mapper.PathListEventDtoMapper;
import by.ares.path_list_service.mapper.PathListRequestMapper;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.repository.SeriaRepository;
import by.ares.path_list_service.service.OutboxEventPublisher;
import by.ares.path_list_service.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static by.ares.path_list_service.util.PathListServiceConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathListCoreService {

    private final PathListRepository pathListRepository;
    private final PathListRequestMapper pathListRequestMapper;
    private final SeriaDtoMapper seriaDtoMapper;
    private final PathListEventDtoMapper pathListEventDtoMapper;
    private final RouteService routeService;
    private final OutboxEventPublisher outboxEventPublisher;
    private final SeriaRepository seriaRepository;

    @Cacheable(value = "path_lists", key = "'path_list:' + #id", sync = true)
    public PathList getRawById(UUID id) {
        return pathListRepository.findById(id)
                .orElseThrow(() -> new PathListNotFoundException(PATH_LIST_NOT_FOUND_MESSAGE));
    }

    public Page<PathList> findAllRawBySeria(SeriaDto seria, Pageable pageable) {
        return pathListRepository.findAllBySeria(seriaDtoMapper.remap(seria), pageable);
    }

    public Page<PathList> findAllRawByDate(LocalDate start, LocalDate end, Pageable pageable) {
        return pathListRepository.findAllByReclamationDateBetween(start, end, pageable);
    }

    @Transactional
    public PathList saveRaw(PathListCreationRequest request) {
        PathList pathList = pathListRequestMapper.map(request);
        Route savedRoute = routeService.save(request.routeCreationRequest());
        pathList.setRoute(savedRoute);
        Seria seria = seriaRepository.findById(request.seriaDto().getId())
                .orElseThrow(() -> new SeriaNotFoundException(SERIA_NOT_FOUND_MESSAGE));
        pathList.setSeria(seria);
        pathList.setReclamationDate(LocalDate.now(ZoneId.systemDefault()));
        pathList.setExpirationDate(pathList.getReclamationDate()
                .plusDays(DEFAULT_EXPIRATION_DAYS));
        var result = pathListRepository.save(pathList);
        var pathListEventDto = pathListEventDtoMapper.map(result);
        log.info(pathListEventDto.toString());
        outboxEventPublisher.invokeAll(pathListEventDto);
        return result;
    }

}