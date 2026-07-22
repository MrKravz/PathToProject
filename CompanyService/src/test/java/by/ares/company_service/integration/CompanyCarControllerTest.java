package by.ares.company_service.integration;


import by.ares.company_service.model.Car;
import by.ares.company_service.model.Company;
import by.ares.company_service.repository.CarRepository;
import by.ares.company_service.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static by.ares.company_service.util.TestModelsBuilder.buildCar;
import static by.ares.company_service.util.TestModelsBuilder.buildCompany;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompanyCarControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CarRepository carRepository;
    @Autowired
    public CompanyRepository companyRepository;

    private Car car;
    private Company company;

    @BeforeEach
    void init() {
        car = carRepository.save(buildCar().setId(null));
        company = companyRepository.save(buildCompany().setId(null));
    }

    @Test
    void assign() throws Exception {
        mockMvc.perform(put("/companies/{companyId}/cars/{carId}", company.getId(),
                        car.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.cars", hasSize(1)));
    }

    @Test
    void unassign() throws Exception {
        mockMvc.perform(delete("/companies/{companyId}/cars/{carId}", company.getId(),
                        car.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.cars", hasSize(0)));
    }
}