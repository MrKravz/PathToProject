package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.EventListener;
import by.ares.document_service.service.impl.PathListSchedulerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static by.ares.document_service.util.TestConstants.AGGREGATE_ID;
import static by.ares.document_service.util.TestModelsBuilder.buildPathListEventDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PathListSchedulerServiceImplTest {

    private List<EventListener<PathListEventDto>> eventListeners;
    private PathListSchedulerServiceImpl schedulerService;

    @Mock
    private EventListener<PathListEventDto> mockListener1;

    @Mock
    private EventListener<PathListEventDto> mockListener2;

    private PathListEventDto pathListDto;

    @BeforeEach
    void init() {
        eventListeners = new ArrayList<>();
        schedulerService = new PathListSchedulerServiceImpl(eventListeners);
        pathListDto = buildPathListEventDto();
    }

    @Test
    void subscribe_shouldAddListener() {
        schedulerService.subscribe(mockListener1);
        assertEquals(1, eventListeners.size());
        assertTrue(eventListeners.contains(mockListener1));
    }

    @Test
    void unsubscribe_shouldRemoveListener() {
        eventListeners.add(mockListener1);
        schedulerService.unsubscribe(mockListener1);
        assertTrue(eventListeners.isEmpty());
    }

    @Test
    void invoke_shouldCallAllListeners() {
        eventListeners.add(mockListener1);
        eventListeners.add(mockListener2);
        schedulerService.invoke(pathListDto);
        verify(mockListener1).invoke(pathListDto);
        verify(mockListener2).invoke(pathListDto);
    }

    @Test
    void onPathListReceive_shouldLogAndInvokeListeners() {
        eventListeners.add(mockListener1);
        schedulerService.onPathListReceive(AGGREGATE_ID, pathListDto);
        verify(mockListener1).invoke(pathListDto);
    }
}