package by.ares.document_service.unit;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.DocumentGeneratorService;
import by.ares.document_service.service.PathListService;
import by.ares.document_service.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;

import static by.ares.document_service.util.TestConstants.EXISTING_PATH_LIST_ID;
import static by.ares.document_service.util.TestModelsBuilder.buildFileResponse;
import static by.ares.document_service.util.TestModelsBuilder.buildPathListEventDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private PathListService pathListService;

    @Mock
    private DocumentGeneratorService documentGeneratorService;

    @InjectMocks
    private DocumentServiceImpl documentService;

    private PathListEventDto pathListDto;
    private FileResponse expectedResponse;

    @BeforeEach
    void init() {
        String targetDir = "/tmp/documents";
        ReflectionTestUtils.setField(documentService, "targetDirectory", targetDir);
        pathListDto = buildPathListEventDto();
        expectedResponse = buildFileResponse();
    }

    @Test
    void findById_shouldReturnExistingDocument_whenFileExists() {
        when(documentGeneratorService.documentExists(any(Path.class))).thenReturn(true);
        FileResponse result = documentService.findById(EXISTING_PATH_LIST_ID);
        assertEquals(EXISTING_PATH_LIST_ID, result.getDocumentId());
        verifyNoInteractions(pathListService);
        verify(documentGeneratorService, never()).generatePathListDocument(any());
    }

    @Test
    void findById_shouldGenerateAndReturnDocument_whenFileDoesNotExist() {
        when(documentGeneratorService.documentExists(any(Path.class))).thenReturn(false);
        when(pathListService.findById(EXISTING_PATH_LIST_ID)).thenReturn(pathListDto);
        when(documentGeneratorService.generatePathListDocument(pathListDto)).thenReturn(expectedResponse);
        FileResponse result = documentService.findById(EXISTING_PATH_LIST_ID);
        assertEquals(expectedResponse, result);
        verify(pathListService).findById(EXISTING_PATH_LIST_ID);
        verify(documentGeneratorService).generatePathListDocument(pathListDto);
    }
}