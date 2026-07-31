package by.ares.document_service.service;

import by.ares.document_service.dto.PathListDto;

public interface PathListSchedulerService {

    void onPathListReceive(String aggregateId, PathListDto pathList);
}
