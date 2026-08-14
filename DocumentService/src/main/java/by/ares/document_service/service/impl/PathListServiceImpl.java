package by.ares.document_service.service.impl;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.exception.PathListNotFoundException;
import by.ares.document_service.mapper.PathListMapper;
import by.ares.document_service.repository.PathListRepository;
import by.ares.document_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static by.ares.document_service.util.DocumentServiceConstants.PATH_LIST_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class PathListServiceImpl implements PathListService {

    private final PathListRepository pathListRepository;
    private final PathListMapper pathListMapper;

    @Override
    public PathListEventDto findById(UUID id) {
        return pathListRepository.findById(id)
                .map(pathListMapper::map)
                .orElseThrow(() -> new PathListNotFoundException(PATH_LIST_NOT_FOUND_MESSAGE));
    }

    @Override
    public void save(PathListEventDto pathListDto) {
        pathListRepository.save(pathListMapper.remap(pathListDto));
    }

}
