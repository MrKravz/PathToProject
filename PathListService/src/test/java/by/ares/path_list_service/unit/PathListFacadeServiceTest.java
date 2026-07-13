package by.ares.path_list_service.unit;
import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.mapper.PathListDtoMapper;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.service.impl.PathListCoreService;
import by.ares.path_list_service.service.impl.PathListFacadeService;
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
import java.util.UUID;

import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PathListFacadeServiceTest {

    @Mock
    private PathListCoreService coreService;
    @Mock
    private PathListDtoMapper pathListDtoMapper;
    @Mock
    private CompanyClient companyClient;
    @Mock
    private CarClient carClient;
    @Mock
    private CarDriverClient carDriverClient;

    @InjectMocks
    private PathListFacadeService facade;

    private PathList pathList;
    private PathListDto pathListDto;
    private PathListCreationRequest pathListCreationRequest;
    private SeriaDto seriaDto;
    private CompanyDto companyDto;
    private CarDto carDto;
    private CarDriverDto carDriverDto;

    @BeforeEach
    void init() {
        seriaDto = buildSeriaDto();
        carDto = buildCarDto();
        carDriverDto = buildCarDriverDto();
        companyDto = buildCompanyDto(carDto, carDriverDto);
        pathList = buildPathList(buildSeria(), buildRoute());
        pathListDto = buildPathListDto();
        pathListCreationRequest = buildPathListCreationRequest(buildRouteCreationRequest(), seriaDto);
    }

    @Test
    void findById_Success() {
        UUID id = UUID.randomUUID();
        when(coreService.getRawById(id)).thenReturn(pathList);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        when(companyClient.findById(pathList.getCompanyId())).thenReturn(companyDto);
        PathListDto result = facade.findById(id);
        assertNotNull(result);
        assertEquals(companyDto, result.getCompanyDto());
        assertEquals(carDto, result.getCarDto());
        assertEquals(carDriverDto, result.getCarDriverDto());
        verify(coreService).getRawById(id);
        verify(pathListDtoMapper).map(pathList);
        verify(companyClient).findById(pathList.getCompanyId());
    }

    @Test
    void findAllBySeria() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> rawPage = new PageImpl<>(List.of(pathList));
        when(coreService.findAllRawBySeria(seriaDto, pageable)).thenReturn(rawPage);
        when(companyClient.findAllById(List.of(pathList.getCompanyId()))).thenReturn(List.of(companyDto));
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        Page<PathListDto> result = facade.findAllBySeria(seriaDto, pageable);
        assertEquals(1, result.getContent().size());
        PathListDto enrichedDto = result.getContent().getFirst();
        assertEquals(companyDto, enrichedDto.getCompanyDto());
        verify(coreService).findAllRawBySeria(seriaDto, pageable);
        verify(companyClient).findAllById(List.of(pathList.getCompanyId()));
    }

    @Test
    void findAllByCreationDateRange() {
        LocalDate start = LocalDate.now().minusDays(5);
        LocalDate end = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<PathList> rawPage = new PageImpl<>(List.of(pathList));
        when(coreService.findAllRawByDate(start, end, pageable)).thenReturn(rawPage);
        when(companyClient.findAllById(List.of(pathList.getCompanyId()))).thenReturn(List.of(companyDto));
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        Page<PathListDto> result = facade.findAllByCreationDateRange(start, end, pageable);
        assertEquals(1, result.getContent().size());
        verify(coreService).findAllRawByDate(start, end, pageable);
        verify(companyClient).findAllById(List.of(pathList.getCompanyId()));
    }

    @Test
    void save_Success() {
        when(coreService.saveRaw(pathListCreationRequest)).thenReturn(pathList);
        when(carClient.findById(pathListCreationRequest.carId())).thenReturn(carDto);
        when(carDriverClient.findById(pathListCreationRequest.carDriverId())).thenReturn(carDriverDto);
        when(companyClient.findById(pathListCreationRequest.companyId())).thenReturn(companyDto);
        when(pathListDtoMapper.map(pathList)).thenReturn(pathListDto);
        PathListDto result = facade.save(pathListCreationRequest);
        assertEquals(companyDto, result.getCompanyDto());
        assertEquals(carDto, result.getCarDto());
        assertEquals(carDriverDto, result.getCarDriverDto());
        verify(coreService).saveRaw(pathListCreationRequest);
        verify(carClient).findById(pathListCreationRequest.carId());
        verify(carDriverClient).findById(pathListCreationRequest.carDriverId());
        verify(companyClient).findById(pathListCreationRequest.companyId());
        verify(pathListDtoMapper).map(pathList);
    }
}
