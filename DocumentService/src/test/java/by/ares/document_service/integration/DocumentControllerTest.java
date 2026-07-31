package by.ares.document_service.integration;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static by.ares.document_service.util.TestConstants.EXISTING_PATH_LIST_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentControllerTest extends AbstractIntegrationTest {

    @MockitoBean
    private DocumentService documentService;

    @Test
    void getDocument_shouldReturnFileResponse_whenValidId() throws Exception {
        String expectedFileName = String.format("path_list_%s.docx", EXISTING_PATH_LIST_ID);
        String expectedDownloadUrl = "/tmp/documents/" + expectedFileName;
        FileResponse expectedResponse = FileResponse.builder()
                .documentId(EXISTING_PATH_LIST_ID)
                .fileName(expectedFileName)
                .downloadUrl(expectedDownloadUrl)
                .build();
        when(documentService.findById(EXISTING_PATH_LIST_ID)).thenReturn(expectedResponse);
        mockMvc.perform(get("/documents/{id}", EXISTING_PATH_LIST_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value(EXISTING_PATH_LIST_ID.toString()))
                .andExpect(jsonPath("$.fileName").value(expectedFileName))
                .andExpect(jsonPath("$.downloadUrl").value(expectedDownloadUrl));
        verify(documentService).findById(EXISTING_PATH_LIST_ID);
    }
}
