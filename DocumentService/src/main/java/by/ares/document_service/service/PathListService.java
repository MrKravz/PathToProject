package by.ares.document_service.service;

import by.ares.document_service.dto.PathListDto;

import java.util.UUID;

public interface PathListService {
    PathListDto findById(UUID id);

    void save(PathListDto pathListDto);
}
