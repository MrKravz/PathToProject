package by.ares.company_service.integration;

import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.model.Car;
import by.ares.company_service.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static by.ares.company_service.util.TestConstants.*;
import static by.ares.company_service.util.TestModelsBuilder.buildCarCreationRequest;
import static by.ares.company_service.util.TestModelsBuilder.buildUpdateCarRequest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class CarControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CarRepository carRepository;

    private Car car;

    @BeforeEach
    void init() {
        car = saveCar();
    }

    private Car saveCar() {
        return carRepository.save(saveCar());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/cars/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.mark").value(CAR_MARK))
                .andExpect(jsonPath("$.mileage").value(String.valueOf(CAR_MILEAGE)));
    }

    @Test
    void save() throws Exception {
        CarCreationRequest request = buildCarCreationRequest();
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.mark").value(CAR_MARK))
                .andExpect(jsonPath("$.mileage").value(String.valueOf(CAR_MILEAGE)));
    }

    @Test
    void update() throws Exception {
        UpdateCarRequest request = buildUpdateCarRequest();
        mockMvc.perform(put("/cars/{id}", car.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.mark").value(CAR_MARK))
                .andExpect(jsonPath("$.mileage").value(String.valueOf(UPDATED_CAR_MILEAGE)));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/cars/{id}", car.getId()))
                .andExpect(status().isNoContent());
    }

}