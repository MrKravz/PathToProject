package by.ares.company_service.integration;

import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static by.ares.company_service.util.TestConstants.*;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompanyControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CompanyRepository companyRepository;

    private Company saveCompany() {
        return companyRepository.save(buildCompany().setId(null));
    }

    @Test
    void findAllById() throws Exception {
        Company company = saveCompany();
        mockMvc.perform(get("/companies")
                        .param("ids", company.getId().toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].identifier").value(COMPANY_IDENTIFIER))
                .andExpect(jsonPath("$[0].companyName").value(COMPANY_NAME))
                .andExpect(jsonPath("$[0].address").value(COMPANY_ADDRESS))
                .andExpect(jsonPath("$[0].phoneNumber").value(COMPANY_PHONE_NUMBER));
    }

    @Test
    void findById() throws Exception {
        Company company = saveCompany();
        mockMvc.perform(get("/companies/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.identifier").value(COMPANY_IDENTIFIER))
                .andExpect(jsonPath("$.companyName").value(COMPANY_NAME))
                .andExpect(jsonPath("$.address").value(COMPANY_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(COMPANY_PHONE_NUMBER));
    }

    @Test
    void save() throws Exception {
        CompanyCreationRequest request = buildCompanyCreationRequest();
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.identifier").value(COMPANY_IDENTIFIER))
                .andExpect(jsonPath("$.companyName").value(COMPANY_NAME))
                .andExpect(jsonPath("$.address").value(COMPANY_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(COMPANY_PHONE_NUMBER));
    }

    @Test
    void update() throws Exception {
        Company company = saveCompany();
        UpdateCompanyRequest request = buildUpdateCompanyRequest();
        mockMvc.perform(put("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.identifier").value(COMPANY_IDENTIFIER))
                .andExpect(jsonPath("$.companyName").value(COMPANY_NAME))
                .andExpect(jsonPath("$.address").value(UPDATED_COMPANY_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(COMPANY_PHONE_NUMBER));
    }

    @Test
    void deleteById() throws Exception {
        Company company = saveCompany();
        mockMvc.perform(delete("/companies/{id}", company.getId()))
                .andExpect(status().isNoContent());
    }
}