package by.ares.document_service.service;

import by.ares.document_service.dto.FileResponse;

import java.util.UUID;

public interface DocumentService {
    FileResponse findById(UUID id);
}
