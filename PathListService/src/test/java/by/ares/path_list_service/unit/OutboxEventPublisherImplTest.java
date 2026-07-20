package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.service.EventListener;
import by.ares.path_list_service.service.impl.OutboxEventPublisherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OutboxEventPublisherImplTest {

    @Mock
    private EventListener<PathListDto> listenerOne;
    @Mock
    private EventListener<PathListDto> listenerTwo;

    private List<EventListener<PathListDto>> eventListeners;
    private OutboxEventPublisherImpl outboxEventPublisher;

    @BeforeEach
    void setUp() {
        eventListeners = new ArrayList<>();
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
        PathListDto pathListDto = new PathListDto();
        outboxEventPublisher.invokeAll(pathListDto);
        verify(listenerOne, times(1)).invoke(pathListDto);
        verify(listenerTwo, times(1)).invoke(pathListDto);
    }
}