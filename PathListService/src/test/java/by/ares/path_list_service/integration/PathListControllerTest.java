package by.ares.path_list_service.integration;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Route;
import by.ares.path_list_service.model.Seria;
import by.ares.path_list_service.repository.PathListRepository;
import by.ares.path_list_service.repository.RouteRepository;
import by.ares.path_list_service.repository.SeriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PathListControllerTest extends AbstractIntegrationTest {

    @Autowired
    private PathListRepository pathListRepository;

    @Autowired
    private SeriaRepository seriaRepository;

    @Autowired
    private RouteRepository routeRepository;

    private PathList savePathList() {
        Seria seria = seriaRepository.save(buildSeria().setId(null));
        Route route = routeRepository.save(buildRoute().setId(null));
        PathList pathList = buildPathList(seria, route)
                .setId(null)
                .setReclamationDate(LocalDate.now());
        return pathListRepository.save(pathList);
    }

    @Test
    void findAllBySeria() throws Exception {
        PathList pathList = savePathList();
        stubFindAllByIdList();
        mockMvc.perform(get("/path_lists/filter_by/seria")
                        .param("id", pathList.getSeria().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(pathList.getId().toString()))
                .andExpect(jsonPath("$.content[0].seria.id").value(pathList.getSeria().getId()));
    }

    @Test
    void findAllByDate() throws Exception {
        PathList pathList = savePathList();
        LocalDate today = LocalDate.now();
        stubFindAllByIdList();
        mockMvc.perform(get("/path_lists/filter_by/date")
                        .param("start", today.minusDays(1).toString())
                        .param("end", today.plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(pathList.getId().toString()))
                .andExpect(jsonPath("$.content[0].reclamationDate").value(today.toString()));
    }

    @Test
    void findById() throws Exception {
        PathList pathList = savePathList();
        stubFindCompanyById();
        mockMvc.perform(get("/path_lists/{uuid}", pathList.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pathList.getId().toString()))
                .andExpect(jsonPath("$.number").value(pathList.getNumber()));
    }

    @Test
    void save() throws Exception {
        Seria seria = seriaRepository.save(buildSeria().setId(null));
        stubFindCarById();
        stubFindCarDriverById();
        stubFindCompanyById();
        SeriaDto seriaDto = SeriaDto.builder().id(seria.getId()).build();
        PathListCreationRequest validRequest = buildPathListCreationRequest(buildRouteCreationRequest(), seriaDto);
        mockMvc.perform(post("/path_lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.number").value(validRequest.number()))
                .andExpect(jsonPath("$.carDto").exists())
                .andExpect(jsonPath("$.route.startPoint").exists())
                .andExpect(jsonPath("$.seria.id").value(seria.getId()));
    }
}