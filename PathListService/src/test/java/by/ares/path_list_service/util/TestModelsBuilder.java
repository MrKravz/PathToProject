package by.ares.path_list_service.util;

import by.ares.path_list_service.dto.*;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.dto.request.RouteCreationRequest;
import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;

import java.util.HashSet;
import java.util.Set;

import static by.ares.path_list_service.util.TestConstants.*;

public class TestModelsBuilder {

    public static Seria buildSeria() {
        return Seria.builder()
                .id(SERIA_ID)
                .name(SERIA_NAME)
                .pathLists(new HashSet<>())
                .build();
    }

    public static SeriaDto buildSeriaDto() {
        return SeriaDto.builder()
                .id(SERIA_ID)
                .name(SERIA_NAME)
                .build();
    }

    public static SeriaCreationRequest buildSeriaCreationRequest() {
        return new SeriaCreationRequest(SERIA_NAME);
    }

    public static CarDto buildCarDto() {
        return CarDto.builder()
                .id(EXISTING_CAR_ID)
                .carName(CAR_NAME)
                .residentNumber(CAR_RESIDENT_NUMBER)
                .build();
    }

    public static CarDriverDto buildCarDriverDto() {
        return CarDriverDto.builder()
                .id(EXISTING_CAR_DRIVER_ID)
                .name(CAR_DRIVER_NAME)
                .surname(CAR_DRIVER_SURNAME)
                .lastname(CAR_DRIVER_LASTNAME)
                .driverLicenseNumber(CAR_DRIVER_LICENSE_NUMBER)
                .build();
    }

    public static PathList buildPathList(Seria seria, Route route) {
        return PathList.builder()
                .id(EXISTING_PATH_LIST_ID)
                .number(PATH_LIST_NUMBER)
                .seria(seria)
                .route(route)
                .reclamationDate(PATH_LIST_RECLAMATION_DATE)
                .expirationDate(PATH_LIST_RECLAMATION_DATE)
                .carId(EXISTING_CAR_ID)
                .carDriverId(EXISTING_CAR_DRIVER_ID)
                .companyId(EXISTING_COMPANY_ID)
                .documentForm(DOCUMENT_FORM)
                .build();
    }

    public static PathListDto buildPathListDto() {
        return PathListDto.builder()
                .id(EXISTING_PATH_LIST_ID)
                .number(PATH_LIST_NUMBER)
                .reclamationDate(PATH_LIST_RECLAMATION_DATE)
                .seria(buildSeriaDto())
                .route(buildRouteDto())
                .carDto(buildCarDto())
                .carDriverDto(buildCarDriverDto())
                .companyDto(buildCompanyDto(buildCarDto(), buildCarDriverDto()))
                .build();
    }

    public static PathListEventDto buildPathListEventDto() {
        return PathListEventDto.builder()
                .id(EXISTING_PATH_LIST_ID)
                .number(PATH_LIST_NUMBER)
                .reclamationDate(PATH_LIST_RECLAMATION_DATE)
                .seria(buildSeriaDto())
                .route(buildRouteDto())
                .carId(EXISTING_CAR_ID)
                .carDriverId(EXISTING_CAR_DRIVER_ID)
                .companyId(EXISTING_COMPANY_ID)
                .build();
    }

    public static PathListCreationRequest buildPathListCreationRequest(RouteCreationRequest routeCreationRequest,
                                                                       SeriaDto seriaDto) {
        return new PathListCreationRequest(
                PATH_LIST_NUMBER,
                EXISTING_CAR_ID,
                EXISTING_CAR_DRIVER_ID,
                EXISTING_COMPANY_ID,
                routeCreationRequest,
                DOCUMENT_FORM,
                seriaDto
        );
    }

    public static CompanyDto buildCompanyDto(CarDto carDto, CarDriverDto carDriverDto) {
        return CompanyDto.builder()
                .id(EXISTING_COMPANY_ID)
                .identifier(COMPANY_IDENTIFIER)
                .companyName(COMPANY_NAME)
                .address(COMPANY_ADDRESS)
                .phoneNumber(COMPANY_PHONE_NUMBER)
                .cars(Set.of(carDto))
                .carDrivers(Set.of(carDriverDto))
                .build();
    }

    public static Route buildRoute() {
        return Route.builder()
                .id(EXISTING_ROUTE_ID)
                .mileage(CAR_MILEAGE)
                .communicationType(ROUTE_COMMUNICATION_TYPE)
                .transportationType(ROUTE_TRANSPORTATION_TYPE)
                .startPoint(ROUTE_START_POINT)
                .endPoint(ROUTE_END_POINT)
                .build();
    }

    public static RouteDto buildRouteDto() {
        return RouteDto.builder()
                .id(EXISTING_ROUTE_ID)
                .transportationType(ROUTE_TRANSPORTATION_TYPE)
                .startPoint(ROUTE_START_POINT)
                .endPoint(ROUTE_END_POINT)
                .build();
    }

    public static RouteCreationRequest buildRouteCreationRequest() {
        return new RouteCreationRequest(CAR_MILEAGE, ROUTE_COMMUNICATION_TYPE,
                ROUTE_TRANSPORTATION_TYPE, ROUTE_START_POINT, ROUTE_END_POINT);
    }

    private TestModelsBuilder() {
    }
}
