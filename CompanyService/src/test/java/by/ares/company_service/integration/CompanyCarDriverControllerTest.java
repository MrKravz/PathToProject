package by.ares.company_service.integration;

import by.ares.company_service.model.CarDriver;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarDriverRepository;
import by.ares.company_service.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompanyCarDriverControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CarDriverRepository carDriverRepository;
    @Autowired
    public CompanyRepository companyRepository;

    private CarDriver carDriver;
    private Company company;

    @BeforeEach
    void init() {
        carDriver = carDriverRepository.save(buildCarDriver().setId(null));
        company = companyRepository.save(buildCompany().setId(null));
    }

    @Test
    void assign() throws Exception {
        mockMvc.perform(put("/companies/{companyId}/car_drivers/{carDriverId}", company.getId(),
                        carDriver.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.carDrivers", hasSize(1)));
    }

    @Test
    void unassign() throws Exception {
        mockMvc.perform(delete("/companies/{companyId}/car_drivers/{carDriverId}", company.getId(),
                        carDriver.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.carDrivers", hasSize(0)));
    }

}