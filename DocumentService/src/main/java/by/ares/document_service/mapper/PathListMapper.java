package by.ares.document_service.mapper;

import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.model.PathList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PathListMapper {

    @Mapping(target = "carDto", source = "pathList.car")
    @Mapping(target = "carDriverDto", source = "pathList.carDriver")
    @Mapping(target = "companyDto", source = "pathList.company")
    PathListDto map(PathList pathList);

    @Mapping(target = "car", source = "pathListDto.carDto")
    @Mapping(target = "carDriver", source = "pathListDto.carDriverDto")
    @Mapping(target = "company", source = "pathListDto.companyDto")
    PathList remap(PathListDto pathListDto);

}
