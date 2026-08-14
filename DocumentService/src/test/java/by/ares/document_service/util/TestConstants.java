package by.ares.document_service.util;

import java.util.UUID;

public class TestConstants {
    public static final UUID EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final UUID NOT_EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final String AGGREGATE_ID = UUID.randomUUID().toString();
    public static final Long EXISTING_CAR_ID = 1L;
    public static final String CAR_NAME = "Mercedes-Benz";
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
    public static final String TEST_FILE_NAME = "test-file.docx";
    public static final String EXPECTED_FILE_NAME_TEMPLATE = "path_list_%s.docx";
    public static final String FAKE_DIRECTORY_NAME = "not-a-directory.txt";
    public static final String TEMPLATE_NOT_FOUND_ERROR_MSG = "Template not found for form:";
    public static final String FAKE_DOC_LOCATION = "classpath:fake_path/*.*";

    private TestConstants() {
    }
}
