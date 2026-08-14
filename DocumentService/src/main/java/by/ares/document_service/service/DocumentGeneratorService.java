package by.ares.document_service.service;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.dto.PathListEventDto;

import java.nio.file.Path;

public interface DocumentGeneratorService {
    boolean documentExists(Path destinationPath);
    FileResponse generatePathListDocument(PathListEventDto pathListEventDto);
}
