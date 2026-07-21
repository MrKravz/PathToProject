package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.model.OutboxEvent;
import by.ares.path_list_service.repository.OutboxEventRepository;
import by.ares.path_list_service.service.impl.OutboxEventListenerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import tools.jackson.databind.ObjectMapper;

import static by.ares.path_list_service.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxEventListenerImplTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OutboxEventRepository outboxEventRepository;

    @InjectMocks
    private OutboxEventListenerImpl outboxEventListener;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(outboxEventListener, "topicName", TEST_TOPIC);
    }

    @Test
    void invoke_ShouldSaveAndDeleteOutboxEvent_WhenSuccessful() {
        PathListDto pathListDto = new PathListDto();
        pathListDto.setId(EXISTING_PATH_LIST_ID);
        String expectedJson = "{\"id\":\"" + EXISTING_PATH_LIST_ID + "\"}";
        when(objectMapper.writeValueAsString(pathListDto)).thenReturn(expectedJson);
        outboxEventListener.invoke(pathListDto);
        verify(objectMapper).writeValueAsString(pathListDto);
        ArgumentCaptor<OutboxEvent> eventCaptor = ArgumentCaptor.forClass(OutboxEvent.class);
        verify(outboxEventRepository).save(eventCaptor.capture());
        verify(outboxEventRepository).delete(eventCaptor.capture());
        OutboxEvent savedEvent = eventCaptor.getAllValues().get(0);
        OutboxEvent deletedEvent = eventCaptor.getAllValues().get(1);
        assertEquals(savedEvent, deletedEvent);
        assertEquals(TEST_TOPIC, savedEvent.getAggregateType());
        assertEquals(EXISTING_PATH_LIST_ID.toString(), savedEvent.getAggregateId());
        assertEquals(TEST_MESSAGE_HEADER, savedEvent.getType());
        assertEquals(expectedJson, savedEvent.getPayload());
    }

}