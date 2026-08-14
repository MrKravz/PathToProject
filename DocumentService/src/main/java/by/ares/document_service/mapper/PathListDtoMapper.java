package by.ares.document_service.mapper;

import by.ares.document_service.client.CompanyClient;
import by.ares.document_service.dto.CompanyDto;
import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.dto.PathListEventDto;
import lombok.Setter;
import org.mapstruct.*;

@Setter
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PathListDtoMapper {

    protected CompanyClient companyClient;

    @Mapping(target = "companyDto", source = "companyId", qualifiedByName = "fetchCompany")
    @Mapping(target = "carDto", ignore = true)
    @Mapping(target = "carDriverDto", ignore = true)
    public abstract PathListDto toDto(PathListEventDto event);

    @Named("fetchCompany")
    protected CompanyDto fetchCompany(Long companyId) {
        if (companyId == null) return null;
        return companyClient.findById(companyId);
    }

    @AfterMapping
    protected void enrichWithCarAndDriver(PathListEventDto event,
                                          @MappingTarget PathListDto targetDto) {
        CompanyDto company = targetDto.getCompanyDto();
        if (company != null) {
            if (event.getCarId() != null && company.getCars() != null) {
                targetDto.setCarDto(
                        company.getCars().stream()
                                .filter(car ->
                                        car.getId().equals(event.getCarId()))
                                .findFirst()
                                .orElse(null)
                );
            }
            if (event.getCarDriverId() != null && company.getCarDrivers() != null) {
                targetDto.setCarDriverDto(
                        company.getCarDrivers().stream()
                                .filter(driver ->
                                        driver.getId().equals(event.getCarDriverId()))
                                .findFirst()
                                .orElse(null)
                );
            }
        }
    }
}
