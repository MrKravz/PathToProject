package by.ares.company_service.util;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.*;
import by.ares.company_service.model.Car;
import by.ares.company_service.model.CarDriver;
import by.ares.company_service.model.Company;

import java.util.HashSet;
import java.util.Set;

import static by.ares.company_service.util.TestConstants.*;

public class TestModelsBuilder {
    public static CarDriver buildCarDriver() {
        return CarDriver.builder()
                .id(EXISTING_CAR_DRIVER_ID)
                .name(CAR_DRIVER_NAME)
                .surname(CAR_DRIVER_SURNAME)
                .lastname(CAR_DRIVER_LASTNAME)
                .driverLicenseNumber(CAR_DRIVER_LICENSE_NUMBER)
                .companies(mockCompanies())
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

    public static CarDriverCreationRequest buildCarDriverCreationRequest() {
        return new CarDriverCreationRequest(CAR_DRIVER_NAME, CAR_DRIVER_SURNAME,
                CAR_DRIVER_LASTNAME, CAR_DRIVER_LICENSE_NUMBER);
    }

    public static UpdateCarDriverRequest buildUpdateCarDriverRequest() {
        return new UpdateCarDriverRequest(CAR_DRIVER_NAME, CAR_DRIVER_SURNAME,
                UPDATED_CAR_DRIVER_LASTNAME);
    }

    public static Company buildCompany() {
        return Company.builder()
                .id(EXISTING_COMPANY_ID)
                .identifier(COMPANY_IDENTIFIER)
                .fullName(COMPANY_FULL_NAME)
                .companyName(COMPANY_NAME)
                .address(COMPANY_ADDRESS)
                .phoneNumber(COMPANY_PHONE_NUMBER)
                .cars(new HashSet<>())
                .carDrivers(new HashSet<>())
                .build();
    }

    public static CompanyDto buildCompanyDto() {
        return CompanyDto.builder()
                .id(EXISTING_COMPANY_ID)
                .identifier(COMPANY_IDENTIFIER)
                .fullName(COMPANY_FULL_NAME)
                .companyName(COMPANY_NAME)
                .address(COMPANY_ADDRESS)
                .phoneNumber(COMPANY_PHONE_NUMBER)
                .cars(new HashSet<>())
                .carDrivers(new HashSet<>())
                .build();
    }

    public static CompanyCreationRequest buildCompanyCreationRequest() {
        return new CompanyCreationRequest(COMPANY_IDENTIFIER, COMPANY_NAME,
                COMPANY_FULL_NAME, COMPANY_ADDRESS, COMPANY_PHONE_NUMBER);

    }

    public static UpdateCompanyRequest buildUpdateCompanyRequest() {
        return new UpdateCompanyRequest(COMPANY_NAME, COMPANY_FULL_NAME,
                UPDATED_COMPANY_ADDRESS, COMPANY_PHONE_NUMBER);

    }

    public static CarDto buildCarDto() {
        return CarDto.builder()
                .id(EXISTING_CAR_ID)
                .mark(CAR_MARK)
                .mileage(CAR_MILEAGE)
                .residentNumber(CAR_RESIDENT_NUMBER)
                .build();
    }

    public static Car buildCar() {
        return Car.builder()
                .id(EXISTING_CAR_ID)
                .mark(CAR_MARK)
                .mileage(CAR_MILEAGE)
                .residentNumber(CAR_RESIDENT_NUMBER)
                .companies(mockCompanies())
                .build();
    }

    public static UpdateCarRequest buildUpdateCarRequest() {
        return new UpdateCarRequest(CAR_MARK, UPDATED_CAR_MILEAGE, CAR_TYPE);
    }

    public static CarCreationRequest buildCarCreationRequest() {
        return new CarCreationRequest(CAR_MARK, CAR_RESIDENT_NUMBER,
                CAR_MILEAGE, CAR_TYPE);
    }

    public static Set<Company> mockCompanies() {
        var companies = new HashSet<Company>();
        companies.add(new Company().setId(EXISTING_COMPANY_ID));
        companies.add(new Company().setId(NOT_EXISTING_COMPANY_ID));
        return companies;
    }

    public static Set<CompanyDto> mockCompaniesDto() {
        var companies = new HashSet<CompanyDto>();
        companies.add(CompanyDto.builder().id(EXISTING_COMPANY_ID).build());
        companies.add(CompanyDto.builder().id(NOT_EXISTING_COMPANY_ID).build());
        return companies;
    }

}
