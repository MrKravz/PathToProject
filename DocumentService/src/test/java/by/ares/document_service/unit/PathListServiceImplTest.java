package by.ares.document_service.unit;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.exception.PathListNotFoundException;
import by.ares.document_service.mapper.PathListMapper;
import by.ares.document_service.model.PathList;
import by.ares.document_service.repository.PathListRepository;
import by.ares.document_service.service.impl.PathListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.ares.document_service.util.TestConstants.EXISTING_PATH_LIST_ID;
import static by.ares.document_service.util.TestConstants.NOT_EXISTING_PATH_LIST_ID;
import static by.ares.document_service.util.TestModelsBuilder.buildPathList;
import static by.ares.document_service.util.TestModelsBuilder.buildPathListDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PathListServiceImplTest {
    @Mock
    private PathListRepository pathListRepository;

    @Mock
    private PathListMapper pathListMapper;

    @InjectMocks
    private PathListServiceImpl pathListService;

    private PathListDto pathListDto;
    private PathList pathList;

    @BeforeEach
    void init() {
        pathList = buildPathList();
        pathListDto = buildPathListDto();
    }

    @Test
    void findById_shouldReturnPathListDto() {
        when(pathListRepository.findById(EXISTING_PATH_LIST_ID)).thenReturn(Optional.of(pathList));
        when(pathListMapper.map(pathList)).thenReturn(pathListDto);
        PathListDto result = pathListService.findById(EXISTING_PATH_LIST_ID);
        assertEquals(pathListDto, result);
        verify(pathListRepository).findById(EXISTING_PATH_LIST_ID);
        verify(pathListMapper).map(pathList);
    }

    @Test
    void findById_shouldThrowExceptionWhenNotFound() {
        when(pathListRepository.findById(NOT_EXISTING_PATH_LIST_ID)).thenReturn(Optional.empty());
        assertThrows(PathListNotFoundException.class,
                () -> pathListService.findById(NOT_EXISTING_PATH_LIST_ID));
        verify(pathListRepository).findById(NOT_EXISTING_PATH_LIST_ID);
        verifyNoInteractions(pathListMapper);
    }

    @Test
    void save_shouldSavePathList() {
        when(pathListMapper.remap(pathListDto)).thenReturn(pathList);
        pathListService.save(pathListDto);
        verify(pathListMapper).remap(pathListDto);
        verify(pathListRepository).save(pathList);
    }
}