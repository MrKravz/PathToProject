package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.mapper.RouteRequestMapper;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.repository.RouteRepository;
import by.ares.path_list_service.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.ares.path_list_service.util.TestModelsBuilder.buildRoute;
import static by.ares.path_list_service.util.TestModelsBuilder.buildRouteCreationRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {
    @Mock
    private RouteRepository routeRepository;

    @Mock
    private RouteRequestMapper routeRequestMapper;

    @InjectMocks
    private RouteServiceImpl routeService;

    private Route route;
    private RouteCreationRequest routeCreationRequest;

    @BeforeEach
    void init() {
        routeCreationRequest = buildRouteCreationRequest();
        route = buildRoute();
    }

    @Test
    void save_Success() {
        when(routeRequestMapper.map(routeCreationRequest)).thenReturn(route);
        when(routeRepository.save(route)).thenReturn(route);
        Route result = routeService.save(routeCreationRequest);
        assertNotNull(result);
        assertEquals(route, result);
        verify(routeRequestMapper).map(routeCreationRequest);
        verify(routeRepository).save(route);
    }
}