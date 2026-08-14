package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.EventListener;
import by.ares.document_service.service.EventPublisher;
import by.ares.document_service.service.PathListSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathListSchedulerServiceImpl implements PathListSchedulerService,
        EventPublisher<PathListEventDto> {


    private final List<EventListener<PathListEventDto>> eventListeners;

    @Override
    @KafkaListener(topics = "path_list")
    public void onPathListReceive(@Header(KafkaHeaders.RECEIVED_KEY) String aggregateId,
                                  PathListEventDto pathListEventDto) {
        log.info("Key: {}", aggregateId);
        log.info("Data: {}", pathListEventDto);
        invoke(pathListEventDto);
    }

    @Override
    public void subscribe(EventListener<PathListEventDto> eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener<PathListEventDto> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void invoke(PathListEventDto pathListEventDto) {
        eventListeners.forEach(x -> x.invoke(pathListEventDto));
    }
    
}
