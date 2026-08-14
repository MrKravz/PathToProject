package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.service.EventListener;
import by.ares.document_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistentListener implements EventListener<PathListEventDto> {
    private final PathListService pathListService;

    @Override
    public void invoke(PathListEventDto pathListDto) {
        pathListService.save(pathListDto);
    }
}
