package by.ares.document_service.util;

import by.ares.document_service.dto.*;
import by.ares.document_service.model.DocumentForm;
import by.ares.document_service.model.PathList;

import java.util.Set;

import static by.ares.document_service.util.TestConstants.*;

public class TestModelsBuilder {

    public static PathListEventDto buildPathListEventDto() {
        return PathListEventDto.builder()
                .id(EXISTING_PATH_LIST_ID)
                .documentForm(DocumentForm.FORM_4P)
                .build();
    }

    public static PathList buildPathList() {
        return PathList.builder()
                .build();
    }
    public static FileResponse buildFileResponse() {
        return FileResponse.builder().build();
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

    private TestModelsBuilder() {}

}
