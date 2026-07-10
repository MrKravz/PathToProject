package by.ares.path_list_service.integration;

import by.ares.path_list_service.dto.CarDriverDto;
import by.ares.path_list_service.dto.CarDto;
import by.ares.path_list_service.dto.CompanyDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static by.ares.path_list_service.util.PathListServiceConstants.*;
import static by.ares.path_list_service.util.TestModelsBuilder.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected static WireMockServer wireMockServer =
            new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));

    private static CarDto carDto;
    private static CarDriverDto carDriverDto;
    private static CompanyDto companyDto;

    @BeforeAll
    static void init() {
        carDto = buildCarDto();
        carDriverDto = buildCarDriverDto();
        companyDto = buildCompanyDto(carDto, carDriverDto);
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @ServiceConnection
    public static final PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:15"));


    protected void stubFindCarById() {
        wireMockServer.stubFor(get(urlEqualTo("/cars/" + CAR_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carDto))));
    }

    protected void stubFindCarDriverById() {
        wireMockServer.stubFor(get(urlEqualTo("/car_drivers/" + CAR_DRIVER_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(carDriverDto))));
    }

    protected void stubFindCompanyById() {
        wireMockServer.stubFor(get(urlEqualTo("/companies/" + COMPANY_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(companyDto))));
    }


    protected void stubFindAllByIdList() {
        wireMockServer.stubFor(get(urlPathEqualTo("/companies"))
                .withQueryParam("ids", equalTo(String.valueOf(COMPANY_ID)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(List.of(companyDto)))));
    }

}