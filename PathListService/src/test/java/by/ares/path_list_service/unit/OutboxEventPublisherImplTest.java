package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.PathListEventDto;
import by.ares.path_list_service.service.EventListener;
import by.ares.path_list_service.service.impl.OutboxEventPublisherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static by.ares.path_list_service.util.TestModelsBuilder.buildPathListEventDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OutboxEventPublisherImplTest {

    @Mock
    private EventListener<PathListEventDto> listenerOne;
    @Mock
    private EventListener<PathListEventDto> listenerTwo;

    private List<EventListener<PathListEventDto>> eventListeners;
    private OutboxEventPublisherImpl outboxEventPublisher;

    private PathListEventDto pathListEventDto;

    @BeforeEach
    void setUp() {
        eventListeners = new ArrayList<>();
        pathListEventDto = buildPathListEventDto();
        outboxEventPublisher = new OutboxEventPublisherImpl(eventListeners);
    }

    @Test
    void subscribe_ShouldAddListenerToList() {
        outboxEventPublisher.subscribe(listenerOne);
        assertEquals(1, eventListeners.size());
        assertTrue(eventListeners.contains(listenerOne));
    }

    @Test
    void unsubscribe_ShouldRemoveListenerFromList() {
        eventListeners.add(listenerOne);
        eventListeners.add(listenerTwo);
        outboxEventPublisher.unsubscribe(listenerOne);
        assertEquals(1, eventListeners.size());
        assertFalse(eventListeners.contains(listenerOne));
        assertTrue(eventListeners.contains(listenerTwo));
    }

    @Test
    void invokeAll_ShouldTriggerInvokeOnAllSubscribedListeners() {
        eventListeners.add(listenerOne);
        eventListeners.add(listenerTwo);
        outboxEventPublisher.invokeAll(pathListEventDto);
        verify(listenerOne, times(1)).invoke(pathListEventDto);
        verify(listenerTwo, times(1)).invoke(pathListEventDto);
    }
}