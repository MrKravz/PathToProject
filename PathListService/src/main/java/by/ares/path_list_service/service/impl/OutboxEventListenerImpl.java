package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.model.OutboxEvent;
import by.ares.path_list_service.repository.OutboxEventRepository;
import by.ares.path_list_service.service.OutboxEventListener;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import static by.ares.path_list_service.util.PathListServiceConstants.MESSAGE_HEADER;

@Service
@RequiredArgsConstructor
public class OutboxEventListenerImpl implements OutboxEventListener {

    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    @Value("${outbox.topic-name:default_path_list}")
    private String topicName;


    @Override
    @Transactional
    public void invoke(PathListDto pathListDto) {
        String jsonPayload = objectMapper.writeValueAsString(pathListDto);
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateType(topicName)
                .aggregateId(pathListDto.getId().toString())
                .type(MESSAGE_HEADER)
                .payload(jsonPayload)
                .build();
        outboxEventRepository.save(outboxEvent);
        outboxEventRepository.delete(outboxEvent);
    }

}
