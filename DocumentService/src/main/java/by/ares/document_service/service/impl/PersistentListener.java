package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.service.EventListener;
import by.ares.document_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistentListener implements EventListener<PathListDto> {
    private final PathListService pathListService;

    @Override
    public void invoke(PathListDto pathListDto) {
        pathListService.save(pathListDto);
    }
}
