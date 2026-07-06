package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.PathListCreationRequest;
import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PathListServiceImpl implements PathListService {

    private final PathListRepository pathListRepository;


    @Override
    public Page<PathListDto> findAllBySeria(SeriaDto seria, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PathListDto> findAllByCreationDateRange(LocalDate start, LocalDate end,
                                                        Pageable pageable) {
        return null;
    }

    @Override
    public PathListDto findById(UUID id) {
        return null;
    }

    @Override
    public PathListDto save(PathListCreationRequest pathListCreationRequest) {
        return null;
    }
}
