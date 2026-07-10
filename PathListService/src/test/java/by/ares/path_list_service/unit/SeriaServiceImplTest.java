package by.ares.path_list_service.unit;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.exception.SeriaNotFoundException;
import by.ares.path_list_service.mapper.SeriaDtoMapper;
import by.ares.path_list_service.mapper.SeriaRequestMapper;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.SeriaRepository;
import by.ares.path_list_service.service.impl.SeriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static by.ares.path_list_service.util.TestConstants.SERIA_NAME;
import static by.ares.path_list_service.util.TestConstants.NOT_EXISTING_SERIA_NAME;
import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeriaServiceImplTest {

    @Mock
    private SeriaRepository seriaRepository;
    @Mock
    private SeriaDtoMapper seriaDtoMapper;
    @Mock
    private SeriaRequestMapper seriaRequestMapper;

    @InjectMocks
    private SeriaServiceImpl seriaService;

    private Seria seria;
    private SeriaDto seriaDto;
    private SeriaCreationRequest seriaCreationRequest;

    @BeforeEach
    void init() {
        seria = buildSeria();
        seriaDto = buildSeriaDto();
        seriaCreationRequest = buildSeriaCreationRequest();
    }

    @Test
    void findAll() {
        when(seriaRepository.findAll()).thenReturn(List.of(seria));
        when(seriaDtoMapper.map(seria)).thenReturn(seriaDto);
        Set<SeriaDto> result = seriaService.findAll();
        assertEquals(1, result.size());
        verify(seriaRepository).findAll();
        verify(seriaDtoMapper).map(seria);
    }

    @Test
    void findByName_Success() {
        when(seriaRepository.findByName(SERIA_NAME)).thenReturn(Optional.of(seria));
        when(seriaDtoMapper.map(seria)).thenReturn(seriaDto);
        SeriaDto result = seriaService.findByName(SERIA_NAME);
        assertNotNull(result);
        assertEquals(seriaDto, result);
        verify(seriaRepository).findByName(SERIA_NAME);
        verify(seriaDtoMapper).map(seria);
    }

    @Test
    void findByName_ThrowsException_WhenNotFound() {
        when(seriaRepository.findByName(NOT_EXISTING_SERIA_NAME)).thenReturn(Optional.empty());
        assertThrows(SeriaNotFoundException.class, () -> seriaService.findByName(NOT_EXISTING_SERIA_NAME));
        verify(seriaRepository).findByName(NOT_EXISTING_SERIA_NAME);
    }

    @Test
    void save() {
        when(seriaRequestMapper.map(seriaCreationRequest)).thenReturn(seria);
        when(seriaRepository.save(seria)).thenReturn(seria);
        when(seriaDtoMapper.map(seria)).thenReturn(seriaDto);
        SeriaDto result = seriaService.save(seriaCreationRequest);
        assertNotNull(result);
        assertEquals(seriaDto, result);
        verify(seriaRequestMapper).map(seriaCreationRequest);
        verify(seriaRepository).save(seria);
        verify(seriaDtoMapper).map(seria);
    }

}