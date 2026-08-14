package by.ares.document_service.service;

import by.ares.document_service.dto.PathListEventDto;

public interface PathListSchedulerService {

    void onPathListReceive(String aggregateId, PathListEventDto pathList);
}
