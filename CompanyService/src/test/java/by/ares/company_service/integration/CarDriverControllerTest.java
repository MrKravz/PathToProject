package by.ares.company_service.integration;

import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.model.CarDriver;
import by.ares.company_service.repository.CarDriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;

import static by.ares.company_service.util.TestConstants.*;
import static by.ares.company_service.util.TestModelsBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarDriverControllerTest extends AbstractIntegrationTest {

    @Autowired
    public CarDriverRepository carDriverRepository;


    private CarDriver saveCarDriver() {
        return carDriverRepository.save(buildCarDriver()
                .setId(null)
                .setCompanies(new HashSet<>()));
    }

    @Test
    void findById() throws Exception {
        CarDriver carDriver = saveCarDriver();
        mockMvc.perform(get("/car_drivers/{id}", carDriver.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carDriver.getId()))
                .andExpect(jsonPath("$.name").value(CAR_DRIVER_NAME))
                .andExpect(jsonPath("$.surname").value(CAR_DRIVER_SURNAME))
                .andExpect(jsonPath("$.lastname").value(CAR_DRIVER_LASTNAME))
                .andExpect(jsonPath("$.driverLicenseNumber").value(CAR_DRIVER_LICENSE_NUMBER));
    }

    @Test
    void save() throws Exception {
        CarDriverCreationRequest request = buildCarDriverCreationRequest();
        mockMvc.perform(post("/car_drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(CAR_DRIVER_NAME))
                .andExpect(jsonPath("$.surname").value(CAR_DRIVER_SURNAME))
                .andExpect(jsonPath("$.lastname").value(CAR_DRIVER_LASTNAME))
                .andExpect(jsonPath("$.driverLicenseNumber").value(CAR_DRIVER_LICENSE_NUMBER));
    }

    @Test
    void update() throws Exception {
        CarDriver carDriver = saveCarDriver();
        UpdateCarDriverRequest request = buildUpdateCarDriverRequest();
        mockMvc.perform(put("/car_drivers/{id}", carDriver.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(carDriver.getId()))
                .andExpect(jsonPath("$.name").value(CAR_DRIVER_NAME))
                .andExpect(jsonPath("$.surname").value(CAR_DRIVER_SURNAME))
                .andExpect(jsonPath("$.lastname").value(UPDATED_CAR_DRIVER_LASTNAME))
                .andExpect(jsonPath("$.driverLicenseNumber").value(CAR_DRIVER_LICENSE_NUMBER));
    }

    @Test
    void deleteById() throws Exception {
        CarDriver carDriver = saveCarDriver();
        mockMvc.perform(delete("/car_drivers/{id}", carDriver.getId()))
                .andExpect(status().isNoContent());
    }

}