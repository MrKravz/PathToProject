package by.ares.document_service.service.impl;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.exception.DocumentCreationException;
import by.ares.document_service.model.DocumentForm;
import by.ares.document_service.service.DocumentGeneratorService;
import com.deepoove.poi.XWPFTemplate;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

import static by.ares.document_service.util.DocumentServiceConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentGeneratorServiceImpl implements DocumentGeneratorService {

    @Value("${document-service.document.location}")
    private String docLocation;
    @Value("${document-service.document.target-directory}")
    private String targetDirectory;


    private final Map<DocumentForm, Resource> templateRegistry =
            new EnumMap<>(DocumentForm.class);

    @PostConstruct
    private void initTemplateRegistry() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(docLocation);
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null) {
                    int extIndex = filename.lastIndexOf(".");
                    if (filename.startsWith(PATH_LIST_DOCUMENT_PREFIX) && extIndex > 0) {
                        String formValue =
                                filename.substring(PATH_LIST_DOCUMENT_PREFIX.length(), extIndex);
                        DocumentForm formEnum = DocumentForm.fromString(formValue);
                        if (formEnum != null) {
                            templateRegistry.put(formEnum, resource);
                            log.info("Found template for {}: {}", formEnum, filename);
                        }
                    }
                }
            }
            log.info("Successfully loaded {} templates into registry", templateRegistry.size());
        } catch (IOException e) {
            log.error("Failed to scan templates directory during initialization", e);
        }
    }

    @Override
    public boolean documentExists(Path destinationPath) {
        return Files.exists(destinationPath);
    }

    @Override
    public FileResponse generatePathListDocument(PathListDto pathListDto) {
        DocumentForm requiredForm = pathListDto.getDocumentForm();
        Resource templateResource = templateRegistry.get(requiredForm);
        if (templateResource == null || !templateResource.exists()) {
            log.error("Template for form {} not found in static resources", requiredForm);
            throw new DocumentCreationException("Template not found for form: " + requiredForm);
        }
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
            try (InputStream inputStream = templateResource.getInputStream();
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

