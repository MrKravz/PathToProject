package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.exception.PathListNotFoundException;
import by.ares.path_list_service.exception.SeriaNotFoundException;
import by.ares.path_list_service.mapper.PathListRequestMapper;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.repository.SeriaRepository;
import by.ares.path_list_service.service.RouteService;
import by.ares.path_list_service.service.impl.PathListCoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.ares.path_list_service.util.PathListServiceConstants.SERIA_NOT_FOUND_MESSAGE;
import static by.ares.path_list_service.util.TestConstants.*;
import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PathListCoreServiceTest {

    @Mock
    private PathListRepository pathListRepository;
    @Mock
    private PathListRequestMapper pathListRequestMapper;
    @Mock
    private SeriaDtoMapper seriaDtoMapper;
    @Mock
    private RouteService routeService;
    @Mock
    private SeriaRepository seriaRepository;

    @InjectMocks
    private PathListCoreService coreService;

    private PathList pathList;
    private PathListCreationRequest pathListCreationRequest;
    private Seria seria;
    private SeriaDto seriaDto;
    private Route route;
    private RouteCreationRequest routeCreationRequest;

    @BeforeEach
    void init() {
        seria = buildSeria();
        seriaDto = buildSeriaDto();
        route = buildRoute();
        routeCreationRequest = buildRouteCreationRequest();
        pathList = buildPathList(seria, route);
        pathListCreationRequest = buildPathListCreationRequest(routeCreationRequest, seriaDto);
    }

    @Test
    void getRawById_Success() {
        when(pathListRepository.findById(EXISTING_PATH_LIST_ID)).thenReturn(Optional.of(pathList));
        PathList result = coreService.getRawById(EXISTING_PATH_LIST_ID);
        assertEquals(pathList, result);
        verify(pathListRepository).findById(EXISTING_PATH_LIST_ID);
    }

    @Test
    void getRawById_ThrowsException_WhenNotFound() {
        when(pathListRepository.findById(NOT_EXISTING_PATH_LIST_ID)).thenReturn(Optional.empty());
        assertThrows(PathListNotFoundException.class, () -> coreService.getRawById(NOT_EXISTING_PATH_LIST_ID));
        verify(pathListRepository).findById(NOT_EXISTING_PATH_LIST_ID);
    }

    @Test
    void findAllRawBySeria() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> page = new PageImpl<>(List.of(pathList));
        when(seriaDtoMapper.remap(seriaDto)).thenReturn(seria);
        when(pathListRepository.findAllBySeria(seria, pageable)).thenReturn(page);
        Page<PathList> result = coreService.findAllRawBySeria(seriaDto, pageable);
        assertEquals(1, result.getContent().size());
        verify(seriaDtoMapper).remap(seriaDto);
        verify(pathListRepository).findAllBySeria(seria, pageable);
    }

    @Test
    void findAllRawByDate() {
        LocalDate start = LocalDate.now().minusDays(5);
        LocalDate end = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> page = new PageImpl<>(List.of(pathList));
        when(pathListRepository.findAllByReclamationDateBetween(start, end, pageable)).thenReturn(page);
        Page<PathList> result = coreService.findAllRawByDate(start, end, pageable);
        assertEquals(1, result.getContent().size());
        verify(pathListRepository).findAllByReclamationDateBetween(start, end, pageable);
    }

    @Test
    void saveRaw_Success() {
        when(pathListRequestMapper.map(pathListCreationRequest)).thenReturn(pathList);
        when(routeService.save(routeCreationRequest)).thenReturn(route);
        when(seriaRepository.findById(SERIA_ID)).thenReturn(Optional.of(seria));
        when(pathListRepository.save(pathList)).thenReturn(pathList);
        PathList result = coreService.saveRaw(pathListCreationRequest);
        assertNotNull(result);
        assertEquals(pathList, result);
        verify(pathListRequestMapper).map(pathListCreationRequest);
        verify(routeService).save(routeCreationRequest);
        verify(seriaRepository).findById(SERIA_ID);
        verify(pathListRepository).save(pathList);
    }

    @Test
    void saveRaw_WhenSeriaNotFound_ThrowsException() {
        when(pathListRequestMapper.map(pathListCreationRequest)).thenReturn(pathList);
        when(routeService.save(routeCreationRequest)).thenReturn(route);
        when(seriaRepository.findById(SERIA_ID)).thenReturn(Optional.empty());
        SeriaNotFoundException exception = assertThrows(SeriaNotFoundException.class, () ->
                coreService.saveRaw(pathListCreationRequest));
        assertEquals(SERIA_NOT_FOUND_MESSAGE, exception.getMessage());
        verify(pathListRepository, never()).save(any());
    }
}