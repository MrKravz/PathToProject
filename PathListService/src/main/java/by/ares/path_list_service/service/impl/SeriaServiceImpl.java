package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.exception.SeriaNotFoundException;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.mapper.SeriaRequestMapper;
import by.ares.path_list_service.repository.SeriaRepository;
import by.ares.path_list_service.service.SeriaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static by.ares.path_list_service.util.PathListServiceConstants.SERIA_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class SeriaServiceImpl implements SeriaService {

    private final SeriaRepository seriaRepository;
    private final SeriaDtoMapper seriaDtoMapper;
    private final SeriaRequestMapper seriaRequestMapper;

    @Override
    @Cacheable(value = "series_all", key = "'all_series'")
    public Set<SeriaDto> findAll() {
        return seriaRepository.findAll()
                .stream()
                .map(seriaDtoMapper::map)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "series", key = "#name", sync = true)
    public SeriaDto findByName(String name) {
        return seriaRepository.findByName(name)
                .map(seriaDtoMapper::map)
                .orElseThrow(() -> new SeriaNotFoundException(SERIA_NOT_FOUND_MESSAGE));
    }

    @Override
    @Transactional
    @CachePut(value = "series", key = "#result.name")
    @CacheEvict(value = "series_all", allEntries = true)
    public SeriaDto save(SeriaCreationRequest seriaCreationRequest) {
        return seriaDtoMapper.map(
                seriaRepository.save(seriaRequestMapper.map(seriaCreationRequest))
        );
    }
}