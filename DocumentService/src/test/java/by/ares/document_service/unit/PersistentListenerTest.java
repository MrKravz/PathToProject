package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.PathListService;
import by.ares.document_service.service.impl.PersistentListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.ares.document_service.util.TestModelsBuilder.buildPathListEventDto;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersistentListenerTest {

    @Mock
    private PathListService pathListService;

    @InjectMocks
    private PersistentListener persistentListener;

    private PathListEventDto pathListDto;

    @BeforeEach
    void init() {
        pathListDto = buildPathListEventDto();
    }

    @Test
    void invoke() {
        persistentListener.invoke(pathListDto);
        verify(pathListService).save(pathListDto);
    }
}