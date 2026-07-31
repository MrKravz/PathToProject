package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.exception.DocumentCreationException;
import by.ares.document_service.service.impl.DocumentGeneratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static by.ares.document_service.util.TestConstants.EXISTING_PATH_LIST_ID;
import static by.ares.document_service.util.TestModelsBuilder.buildPathListDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentGeneratorServiceImplTest {

    @InjectMocks
    private DocumentGeneratorServiceImpl documentGeneratorService;

    private PathListDto pathListDto;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void init() {
        pathListDto = buildPathListDto();
        ReflectionTestUtils.setField(documentGeneratorService, "targetDirectory", tempDir.toString());
    }

    @Test
    void documentExists_shouldReturnTrueWhenFileExists() throws IOException {
        Path file = tempDir.resolve("test-file.docx");
        Files.createFile(file);
        assertTrue(documentGeneratorService.documentExists(file));
    }

    @Test
    void generatePathListDocument_shouldSkipGeneration_whenFileAlreadyExists() throws IOException {
        String expectedFileName = String.format("path_list_%s.docx", EXISTING_PATH_LIST_ID);
        Path expectedPath = tempDir.resolve(expectedFileName);
        Files.createFile(expectedPath);
        var response = documentGeneratorService.generatePathListDocument(pathListDto);
        assertEquals(EXISTING_PATH_LIST_ID, response.getDocumentId());
        assertEquals(expectedPath.toString(), response.getDownloadUrl());
    }

    @Test
    void generatePathListDocument_shouldThrowException_whenTemplateFails() throws IOException {
        Path fakeDirectory = tempDir.resolve("not-a-directory.txt");
        Files.createFile(fakeDirectory);
        ReflectionTestUtils.setField(documentGeneratorService, "targetDirectory",
                fakeDirectory.toString());
        assertThrows(DocumentCreationException.class,
                () -> documentGeneratorService.generatePathListDocument(pathListDto));
    }
}