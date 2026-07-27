package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.service.DocumentGeneratorService;
import by.ares.document_service.service.EventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneratorListener implements EventListener<PathListDto> {
    private final DocumentGeneratorService documentGeneratorService;

    @Override
    public void invoke(PathListDto pathListDto) {
        documentGeneratorService.generatePathListDocument(pathListDto);
    }
}
