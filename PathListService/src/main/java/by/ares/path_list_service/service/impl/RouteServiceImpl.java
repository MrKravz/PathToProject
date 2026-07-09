package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.RouteDto;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.exception.RouteNotFoundException;
import by.ares.path_list_service.mapper.RouteDtoMapper;
import by.ares.path_list_service.mapper.RouteRequestMapper;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.repository.RouteRepository;
import by.ares.path_list_service.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static by.ares.path_list_service.util.PathListServiceConstants.ROUTE_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteDtoMapper routeDtoMapper;
    private final RouteRequestMapper routeRequestMapper;

    @Override
    public RouteDto findById(UUID id) {
        return routeRepository.findById(id)
                .map(routeDtoMapper::map)
                .orElseThrow(() -> new RouteNotFoundException(ROUTE_NOT_FOUND_MESSAGE));
    }

    @Override
    public Route save(RouteCreationRequest routeCreationRequest) {
        return routeRepository.save(routeRequestMapper.map(routeCreationRequest));
    }

}
