package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListDto;
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
        EventPublisher<PathListDto> {


    private final List<EventListener<PathListDto>> eventListeners;

    @Override
    @KafkaListener(topics = "path_list")
    public void onPathListReceive(@Header(KafkaHeaders.RECEIVED_KEY) String aggregateId,
                                  PathListDto pathListDto) {
        log.info("Key: {}", aggregateId);
        log.info("Data: {}", pathListDto);
        invoke(pathListDto);
    }

    @Override
    public void subscribe(EventListener<PathListDto> eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener<PathListDto> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void invoke(PathListDto pathListDto) {
        eventListeners.forEach(x -> x.invoke(pathListDto));
    }
    
}
