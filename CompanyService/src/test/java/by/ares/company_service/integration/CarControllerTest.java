package by.ares.company_service.integration;

import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.model.Car;
import by.ares.company_service.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;

import static by.ares.company_service.util.TestConstants.*;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CarRepository carRepository;

    private Car saveCar() {
        return carRepository.save(buildCar()
                .setId(null)
                .setCompanies(new HashSet<>()));
    }

    @Test
    void findById() throws Exception {
        Car car = saveCar();
        mockMvc.perform(get("/cars/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.carName").value(CAR_NAME))
                .andExpect(jsonPath("$.carType").value(String.valueOf(CAR_TYPE)));
    }

    @Test
    void save() throws Exception {
        CarCreationRequest request = buildCarCreationRequest();
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.carName").value(CAR_NAME))
                .andExpect(jsonPath("$.carType").value(String.valueOf(CAR_TYPE)));
    }

    @Test
    void update() throws Exception {
        Car car = saveCar();
        UpdateCarRequest request = buildUpdateCarRequest();
        mockMvc.perform(put("/cars/{id}", car.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.carName").value(CAR_NAME))
                .andExpect(jsonPath("$.carType").value(String.valueOf(CAR_TYPE)));
    }

    @Test
    void deleteById() throws Exception {
        Car car = saveCar();
        mockMvc.perform(delete("/cars/{id}", car.getId()))
                .andExpect(status().isNoContent());
    }

}