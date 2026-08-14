package by.ares.document_service.mapper;

import by.ares.document_service.dto.PathListEventDto;
import by.ares.document_service.model.PathList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PathListMapper {

    PathListEventDto map(PathList pathList);

    PathList remap(PathListEventDto pathListDto);

}
