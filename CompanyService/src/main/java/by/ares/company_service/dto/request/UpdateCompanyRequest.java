package by.ares.company_service.dto.request;

public record UpdateCompanyRequest(String companyName, String fullName,
                                   String address, String phoneNumber) {
}
