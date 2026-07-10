package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.mapper.RouteRequestMapper;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.repository.RouteRepository;
import by.ares.path_list_service.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteRequestMapper routeRequestMapper;

    @Override
    public Route save(RouteCreationRequest routeCreationRequest) {
        return routeRepository.save(routeRequestMapper.map(routeCreationRequest));
    }
}
