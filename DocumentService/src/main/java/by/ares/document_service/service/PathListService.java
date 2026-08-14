package by.ares.document_service.service;

import by.ares.document_service.dto.PathListEventDto;

import java.util.UUID;

public interface PathListService {
    PathListEventDto findById(UUID id);

    void save(PathListEventDto pathListDto);
}
