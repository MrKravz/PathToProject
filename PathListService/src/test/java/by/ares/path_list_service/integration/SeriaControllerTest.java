package by.ares.path_list_service.integration;

import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.SeriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static by.ares.path_list_service.util.TestConstants.SERIA_NAME;
import static by.ares.path_list_service.util.TestModelsBuilder.buildSeria;
import static by.ares.path_list_service.util.TestModelsBuilder.buildSeriaCreationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SeriaControllerTest extends AbstractIntegrationTest {

    @Autowired
    public SeriaRepository seriaRepository;

    private Seria saveSeria() {
        return seriaRepository.save(buildSeria()
                .setId(null));
    }

    @Test
    void findAll() throws Exception {
        saveSeria();
        saveSeria();
        mockMvc.perform(get("/serias"))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findByName() throws Exception {
        Seria seria = saveSeria();
        mockMvc.perform(get("/serias/{seriaName}", seria.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(seria.getId()))
                .andExpect(jsonPath("$.name").value(SERIA_NAME));
    }

    @Test
    void save() throws Exception {
        SeriaCreationRequest seriaCreationRequest = buildSeriaCreationRequest();
        mockMvc.perform(post("/serias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seriaCreationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(SERIA_NAME));
    }

}