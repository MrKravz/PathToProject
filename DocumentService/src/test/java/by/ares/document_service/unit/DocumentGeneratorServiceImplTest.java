package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.exception.DocumentCreationException;
import by.ares.document_service.model.DocumentForm;
import by.ares.document_service.service.impl.DocumentGeneratorServiceImpl;
import by.ares.document_service.util.TestModelsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static by.ares.document_service.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentGeneratorServiceImplTest {

    @InjectMocks
    private DocumentGeneratorServiceImpl documentGeneratorService;

    @Mock
    private Resource mockTemplateResource;

    private PathListDto pathListDto;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void init() {
        pathListDto = TestModelsBuilder.buildPathListDto();
        ReflectionTestUtils.setField(documentGeneratorService, "targetDirectory", tempDir.toString());
    }

    @Test
    void documentExists_shouldReturnTrueWhenFileExists() throws IOException {
        Path file = tempDir.resolve(TEST_FILE_NAME);
        Files.createFile(file);

        assertTrue(documentGeneratorService.documentExists(file));
    }

    @Test
    void generatePathListDocument_shouldSkipGeneration_whenFileAlreadyExists() throws IOException {
        populateTemplateRegistry(pathListDto.getDocumentForm(), mockTemplateResource);
        when(mockTemplateResource.exists()).thenReturn(true);

        String expectedFileName = String.format(EXPECTED_FILE_NAME_TEMPLATE, EXISTING_PATH_LIST_ID);
        Path expectedPath = tempDir.resolve(expectedFileName);
        Files.createFile(expectedPath);

        var response = documentGeneratorService.generatePathListDocument(pathListDto);

        assertEquals(EXISTING_PATH_LIST_ID, response.getDocumentId());
        assertEquals(expectedPath.toString(), response.getDownloadUrl());
    }

    @Test
    void generatePathListDocument_shouldThrowException_whenTemplateNotFound() {
        DocumentCreationException exception = assertThrows(DocumentCreationException.class,
                () -> documentGeneratorService.generatePathListDocument(pathListDto));

        assertTrue(exception.getMessage().contains(TEMPLATE_NOT_FOUND_ERROR_MSG));
    }

    @Test
    void generatePathListDocument_shouldThrowException_whenTargetDirectoryIsInvalid() throws IOException {
        populateTemplateRegistry(pathListDto.getDocumentForm(), mockTemplateResource);
        when(mockTemplateResource.exists()).thenReturn(true);

        Path fakeDirectory = tempDir.resolve(FAKE_DIRECTORY_NAME);
        Files.createFile(fakeDirectory);
        ReflectionTestUtils.setField(documentGeneratorService,
                "targetDirectory", fakeDirectory.toString());

        assertThrows(DocumentCreationException.class,
                () -> documentGeneratorService.generatePathListDocument(pathListDto));
    }

    @Test
    void initTemplateRegistry_shouldExecuteGracefully_whenInvalidLocation() {
        ReflectionTestUtils.setField(documentGeneratorService,
                "docLocation", FAKE_DOC_LOCATION);
        assertDoesNotThrow(() ->
                ReflectionTestUtils.invokeMethod(documentGeneratorService,
                        "initTemplateRegistry"));
    }

    @SuppressWarnings("unchecked")
    private void populateTemplateRegistry(DocumentForm form, Resource resource) {
        Map<DocumentForm, Resource> registry =
                (Map<DocumentForm, Resource>) ReflectionTestUtils
                        .getField(documentGeneratorService, "templateRegistry");
        if (registry != null) {
            registry.put(form, resource);
        }
    }
}