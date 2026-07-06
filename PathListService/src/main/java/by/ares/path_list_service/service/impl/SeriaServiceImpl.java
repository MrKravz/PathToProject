package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.SeriaCreationRequest;
import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.service.SeriaService;

import java.util.Set;

public class SeriaServiceImpl implements SeriaService {
    @Override
    public Set<SeriaDto> findAll() {
        return Set.of();
    }

    @Override
    public SeriaDto findByName(String name) {
        return null;
    }

    @Override
    public SeriaDto save(SeriaCreationRequest seriaCreationRequest) {
        return null;
    }
}
