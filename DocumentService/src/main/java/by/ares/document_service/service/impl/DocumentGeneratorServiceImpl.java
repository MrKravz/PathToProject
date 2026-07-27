package by.ares.document_service.service.impl;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.exception.DocumentCreationException;
import by.ares.document_service.service.DocumentGeneratorService;
import com.deepoove.poi.XWPFTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.ares.document_service.util.DocumentServiceConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentGeneratorServiceImpl implements DocumentGeneratorService {

    @Value("${document-service.document.target-directory}")
    private String targetDirectory;

    @Override
    public boolean documentExists(Path destinationPath) {
        return Files.exists(destinationPath);
    }

    @Override
    public FileResponse generatePathListDocument(PathListDto pathListDto) {
        String outputFileName = String.format(PATH_LIST_NAME_TEMPLATE, pathListDto.getId());
        Path targetDirPath = Paths.get(targetDirectory);
        Path destinationPath = targetDirPath.resolve(outputFileName);
        try {
            if (Files.notExists(targetDirPath)) {
                Files.createDirectories(targetDirPath);
            }
            if (documentExists(destinationPath)) {
                log.info("File {} already exists, skipping generation", destinationPath);
                return buildFileResponse(pathListDto, outputFileName, destinationPath);
            }
            ClassPathResource resource = new ClassPathResource(PATH_LIST_TEMPLATE_LOCATION);
            try (InputStream inputStream = resource.getInputStream();
                 OutputStream outputStream = new FileOutputStream(destinationPath.toFile())) {
                XWPFTemplate template = XWPFTemplate.compile(inputStream).render(pathListDto);
                template.write(outputStream);
                template.close();
            }
            log.info("Successfully created document at {}", destinationPath);

        } catch (IOException e) {
            log.error("Failed to generate document for ID {}: {}", pathListDto.getId(), e.getMessage(), e);
            throw new DocumentCreationException(DOCUMENT_CREATION_FAIL_MESSAGE + e.getMessage());
        }
        return buildFileResponse(pathListDto, outputFileName, destinationPath);
    }

    private FileResponse buildFileResponse(PathListDto pathListDto,
                                           String fileName, Path filePath) {
        return FileResponse.builder()
                .documentId(pathListDto.getId())
                .fileName(fileName)
                .downloadUrl(filePath.toString())
                .build();
    }
}

