package by.ares.company_service.dto.request;

public record CompanyCreationRequest(String identifier, String companyName,
                                     String fullName, String address, String phoneNumber) {
}
