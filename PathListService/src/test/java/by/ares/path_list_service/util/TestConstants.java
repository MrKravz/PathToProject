package by.ares.path_list_service.util;

import by.ares.path_list_service.model.TransportationType;

import java.time.LocalDate;
import java.util.UUID;

public class TestConstants {

    public static final Integer SERIA_ID = 1;
    public static final String SERIA_NAME = "УН";
    public static final String NOT_EXISTING_SERIA_NAME = "sd";
    public static final Long EXISTING_CAR_ID = 1L;
    public static final String CAR_MARK = "Mercedes-Benz";
    public static final Integer CAR_MILEAGE = 1000;
    public static final String CAR_RESIDENT_NUMBER = "1234AS";
    public static final Long EXISTING_CAR_DRIVER_ID = 1L;
    public static final String CAR_DRIVER_NAME = "John";
    public static final String CAR_DRIVER_SURNAME = "Pork";
    public static final String CAR_DRIVER_LASTNAME = "Johnson";
    public static final String CAR_DRIVER_LICENSE_NUMBER = "AB123456";
    public static final Long EXISTING_COMPANY_ID = 1L;
    public static final String COMPANY_IDENTIFIER = "1147847423899";
    public static final String COMPANY_NAME = "Strathen Oakment";
    public static final String COMPANY_ADDRESS = "Wall st., h. 3";
    public static final String COMPANY_PHONE_NUMBER = "+375291111111";
    public static final UUID EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final UUID NOT_EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final LocalDate PATH_LIST_RECLAMATION_DATE = LocalDate.now();
    public static final Integer PATH_LIST_NUMBER = 1;
    public static final UUID EXISTING_ROUTE_ID = UUID.randomUUID();
    public static final TransportationType ROUTE_TRANSPORTATION_TYPE = TransportationType.URBAN;
    public static final String ROUTE_START_POINT = "Point A";
    public static final String ROUTE_END_POINT = "Point B";
    public static final String TEST_TOPIC = "test_topic";
    public static final String TEST_MESSAGE_HEADER = "PathListCreated";

    private TestConstants() {
    }
}
