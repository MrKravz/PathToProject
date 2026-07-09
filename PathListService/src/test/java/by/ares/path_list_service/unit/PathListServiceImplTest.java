package by.ares.path_list_service.unit;

import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.exception.PathListNotFoundException;
import by.ares.path_list_service.mapper.PathListDtoMapper;
import by.ares.path_list_service.mapper.PathListRequestMapper;
import by.ares.path_list_service.mapper.RouteRequestMapper;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.PathListRepository;
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
    private RouteRequestMapper routeRequestMapper;

    @InjectMocks
    private PathListServiceImpl pathListService;

    private PathList pathList;
    private PathListDto pathListDto;
    private PathListCreationRequest pathListCreationRequest;
    private Seria seria;

    private SeriaDto seriaDto;
    private Route route;
    private RouteCreationRequest routeCreationRequest;
    private CompanyDto companyDto;
    private CarDto carDto;
    private CarDriverDto carDriverDto;


    @BeforeEach
    void init() {
        seria = buildSeria();
        seriaDto = buildSeriaDto();
        route = buildRoute();
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
    void save() {
        when(carClient.findById(CAR_ID)).thenReturn(carDto);
        when(carDriverClient.findById(CAR_DRIVER_ID)).thenReturn(carDriverDto);
        when(companyClient.findById(COMPANY_ID)).thenReturn(companyDto);
        when(pathListRequestMapper.map(pathListCreationRequest)).thenReturn(pathList);
        when(routeRequestMapper.map(routeCreationRequest)).thenReturn(route);
        when(pathListRepository.save(pathList)).thenReturn(pathList);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        PathListDto result = pathListService.save(pathListCreationRequest);
        assertNotNull(result);
        assertEquals(pathListDto, result);
        verify(carClient).findById(CAR_ID);
        verify(carDriverClient).findById(CAR_DRIVER_ID);
        verify(companyClient).findById(COMPANY_ID);
        verify(pathListRequestMapper).map(pathListCreationRequest);
        verify(pathListRepository).save(pathList);
        verify(pathListDtoMapper).map(pathList);
    }

}