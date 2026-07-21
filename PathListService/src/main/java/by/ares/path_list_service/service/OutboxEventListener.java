package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.PathListDto;

public interface OutboxEventListener extends EventListener<PathListDto> {
}
