package by.ares.path_list_service.mapper;

import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.model.Seria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeriaRequestMapper {
    Seria map(SeriaCreationRequest seriaCreationRequest);
}
