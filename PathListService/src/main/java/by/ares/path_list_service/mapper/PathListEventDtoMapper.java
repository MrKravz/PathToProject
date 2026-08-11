package by.ares.path_list_service.mapper;

import by.ares.path_list_service.dto.PathListEventDto;
import by.ares.path_list_service.model.PathList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RouteDtoMapper.class, SeriaDtoMapper.class})
public interface PathListEventDtoMapper extends DtoMapper<PathList, PathListEventDto> {
}
