package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.PathListEventDto;

public interface OutboxEventListener extends EventListener<PathListEventDto> {
}
