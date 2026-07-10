package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.dto.SeriaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PathListService {

    Page<PathListDto> findAllBySeria(SeriaDto seria, Pageable pageable);
    Page<PathListDto> findAllByCreationDateRange(LocalDate start, LocalDate end,
                                                 Pageable pageable);
    PathListDto findById(UUID id);
    PathListDto save(PathListCreationRequest pathListCreationRequest);

}
