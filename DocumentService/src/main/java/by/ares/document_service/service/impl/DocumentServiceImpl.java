package by.ares.document_service.service.impl;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.service.DocumentGeneratorService;
import by.ares.document_service.service.DocumentService;
import by.ares.document_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static by.ares.document_service.util.DocumentServiceConstants.PATH_LIST_NAME_TEMPLATE;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final PathListService pathListService;
    private final DocumentGeneratorService documentGeneratorService;

    @Value("${document-service.document.target-directory}")
    private String targetDirectory;

    @Override
    public FileResponse findById(UUID id) {
        String outputFileName = String.format(PATH_LIST_NAME_TEMPLATE, id);
        Path targetDirPath = Paths.get(targetDirectory);
        Path destinationPath = targetDirPath.resolve(outputFileName);
        if (documentGeneratorService.documentExists(destinationPath)) {
            return FileResponse.builder()
                    .documentId(id)
                    .fileName(outputFileName)
                    .downloadUrl(destinationPath.toString())
                    .build();
        }
        return documentGeneratorService.generatePathListDocument(pathListService.findById(id));
    }
}
