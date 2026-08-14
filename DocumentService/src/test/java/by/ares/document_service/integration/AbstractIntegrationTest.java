package by.ares.document_service.integration;

import by.ares.document_service.dto.CompanyDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.ObjectMapper;

import static by.ares.document_service.util.DocumentServiceConstants.COMPANY_ID;
import static by.ares.document_service.util.TestModelsBuilder.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

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

    private static CompanyDto companyDto;

    @BeforeAll
    static void init() {
        companyDto = buildCompanyDto(buildCarDto(), buildCarDriverDto());
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @ServiceConnection
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:8"));

    @ServiceConnection
    public static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("apache/kafka:3.7.0"));

    protected void stubFindCompanyById() {
        wireMockServer.stubFor(get(urlEqualTo("/companies/" + COMPANY_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(companyDto))));
    }

}
