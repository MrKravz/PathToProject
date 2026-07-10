package by.ares.path_list_service.unit;

import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.exception.PathListNotFoundException;
import by.ares.path_list_service.mapper.*;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.repository.SeriaRepository;
import by.ares.path_list_service.service.RouteService;
import by.ares.path_list_service.service.impl.PathListServiceImpl;
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
import java.util.UUID;

import static by.ares.path_list_service.util.PathListServiceConstants.*;
import static by.ares.path_list_service.util.TestConstants.SERIA_ID;
import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PathListServiceImplTest {

    @Mock
    private PathListRepository pathListRepository;
    @Mock
    private PathListDtoMapper pathListDtoMapper;
    @Mock
    private PathListRequestMapper pathListRequestMapper;
    @Mock
    private SeriaDtoMapper seriaDtoMapper;
    @Mock
    private CompanyClient companyClient;
    @Mock
    private CarClient carClient;
    @Mock
    private CarDriverClient carDriverClient;
    @Mock
    private  RouteService routeService;
    @Mock
    private SeriaRepository seriaRepository;
    @Mock
    private RouteDtoMapper routeDtoMapper;

    @InjectMocks
    private PathListServiceImpl pathListService;

    private PathList pathList;
    private PathListDto pathListDto;
    private PathListCreationRequest pathListCreationRequest;
    private Seria seria;

    private SeriaDto seriaDto;
    private Route route;
    private RouteDto routeDto;
    private RouteCreationRequest routeCreationRequest;
    private CompanyDto companyDto;
    private CarDto carDto;
    private CarDriverDto carDriverDto;


    @BeforeEach
    void init() {
        seria = buildSeria();
        seriaDto = buildSeriaDto();
        route = buildRoute();
        routeDto = buildRouteDto();
        routeCreationRequest = buildRouteCreationRequest();
        carDto = buildCarDto();
        carDriverDto = buildCarDriverDto();
        companyDto = buildCompanyDto(carDto, carDriverDto);
        pathList = buildPathList(seria, route);
        pathListDto = buildPathListDto();
        pathListCreationRequest = buildPathListCreationRequest(routeCreationRequest, seriaDto);
    }

    @Test
    void findAllBySeria() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> page = new PageImpl<>(List.of(pathList));
        when(seriaDtoMapper.remap(seriaDto)).thenReturn(seria);
        when(pathListRepository.findAllBySeria(seria, pageable)).thenReturn(page);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        when(companyClient.findAllById(List.of(COMPANY_ID))).thenReturn(List.of(companyDto));
        Page<PathListDto> result = pathListService.findAllBySeria(seriaDto, pageable);
        assertEquals(1, result.getContent().size());
        verify(seriaDtoMapper).remap(seriaDto);
        verify(pathListRepository).findAllBySeria(seria, pageable);
        verify(pathListDtoMapper).map(pathList);
        verify(companyClient).findAllById(List.of(COMPANY_ID));
    }

    @Test
    void findAllByCreationDateRange() {
        LocalDate start = LocalDate.now().minusDays(5);
        LocalDate end = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> page = new PageImpl<>(List.of(pathList));
        when(pathListRepository.findAllByReclamationDateBetween(start, end, pageable)).thenReturn(page);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        when(companyClient.findAllById(List.of(COMPANY_ID))).thenReturn(List.of(companyDto));
        Page<PathListDto> result = pathListService.findAllByCreationDateRange(start, end, pageable);
        assertEquals(1, result.getContent().size());
        verify(pathListRepository).findAllByReclamationDateBetween(start, end, pageable);
        verify(pathListDtoMapper).map(pathList);
        verify(companyClient).findAllById(List.of(COMPANY_ID));
    }

    @Test
    void findById_Success() {
        UUID id = UUID.randomUUID();
        when(pathListRepository.findById(id)).thenReturn(Optional.of(pathList));
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        PathListDto result = pathListService.findById(id);
        assertNotNull(result);
        assertEquals(pathListDto, result);
        verify(pathListRepository).findById(id);
        verify(pathListDtoMapper).map(pathList);
    }

    @Test
    void findById_ThrowsException_WhenNotFound() {
        UUID id = UUID.randomUUID();
        when(pathListRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(PathListNotFoundException.class, () -> pathListService.findById(id));
        verify(pathListRepository).findById(id);
        verify(pathListDtoMapper, never()).map(any());
    }

    @Test
    void save_Success() {
        when(carClient.findById(CAR_ID)).thenReturn(carDto);
        when(carDriverClient.findById(CAR_DRIVER_ID)).thenReturn(carDriverDto);
        when(companyClient.findById(COMPANY_ID)).thenReturn(companyDto);
        when(pathListRequestMapper.map(pathListCreationRequest)).thenReturn(pathList);
        when(routeService.save(routeCreationRequest)).thenReturn(route);
        when(seriaRepository.findById(SERIA_ID)).thenReturn(Optional.of(seria));
        when(pathListRepository.save(pathList)).thenReturn(pathList);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        when(routeDtoMapper.map(route)).thenReturn(routeDto);
        when(seriaDtoMapper.map(seria)).thenReturn(seriaDto);
        PathListDto result = pathListService.save(pathListCreationRequest);
        assertNotNull(result);
        assertEquals(pathListDto, result);
        verify(carClient).findById(CAR_ID);
        verify(carDriverClient).findById(CAR_DRIVER_ID);
        verify(companyClient).findById(COMPANY_ID);
        verify(pathListRequestMapper).map(pathListCreationRequest);
        verify(routeService).save(routeCreationRequest);
        verify(seriaRepository).findById(SERIA_ID);
        verify(pathListRepository).save(pathList);
        verify(pathListDtoMapper).map(pathList);
        verify(routeDtoMapper).map(route);
        verify(seriaDtoMapper).map(seria);
    }

    @Test
    void save_WhenSeriaNotFound_ThrowsException() {
        when(carClient.findById(CAR_ID)).thenReturn(carDto);
        when(carDriverClient.findById(CAR_DRIVER_ID)).thenReturn(carDriverDto);
        when(companyClient.findById(COMPANY_ID)).thenReturn(companyDto);
        when(pathListRequestMapper.map(pathListCreationRequest)).thenReturn(pathList);
        when(routeService.save(routeCreationRequest)).thenReturn(route);
        when(seriaRepository.findById(SERIA_ID)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pathListService.save(pathListCreationRequest));
        assertEquals("Seria not found", exception.getMessage());
        verify(pathListRepository, never()).save(any());
        verify(pathListDtoMapper, never()).map(any());
        verify(routeDtoMapper, never()).map(any());
        verify(seriaDtoMapper, never()).map(any());
    }

}