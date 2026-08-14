package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.DocumentGeneratorService;
import by.ares.document_service.service.impl.GeneratorListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.ares.document_service.util.TestModelsBuilder.buildPathListEventDto;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GeneratorListenerTest {
    @Mock
    private DocumentGeneratorService documentGeneratorService;

    @InjectMocks
    private GeneratorListener generatorListener;

    private PathListEventDto pathListDto;

    @BeforeEach
    void init() {
        pathListDto = buildPathListEventDto();
    }

    @Test
    void invoke() {
        generatorListener.invoke(pathListDto);
        verify(documentGeneratorService).generatePathListDocument(pathListDto);
    }
}