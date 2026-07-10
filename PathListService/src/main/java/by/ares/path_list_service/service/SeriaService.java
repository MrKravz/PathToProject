package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.dto.SeriaDto;

import java.util.Set;

public interface SeriaService {

    Set<SeriaDto> findAll();

    SeriaDto findByName(String name);

    SeriaDto save(SeriaCreationRequest seriaCreationRequest);

}
