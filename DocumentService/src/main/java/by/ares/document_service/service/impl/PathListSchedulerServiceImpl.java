package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.service.PathListSchedulerService;
import by.ares.document_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathListSchedulerServiceImpl implements PathListSchedulerService {

    private final PathListService pathListService;

    @Override
    @KafkaListener(topics = "path_list")
    public void onPathListReceive(@Header(KafkaHeaders.RECEIVED_KEY) String aggregateId,
                                  PathListDto pathListDto) {
        log.warn("Key: " + aggregateId);
        log.warn("Data: " + pathListDto);
        pathListService.save(pathListDto);
    }

}
