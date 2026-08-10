package by.ares.document_service.util;

import java.util.UUID;

public class TestConstants {
    public static final UUID EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final UUID NOT_EXISTING_PATH_LIST_ID = UUID.randomUUID();
    public static final String AGGREGATE_ID = UUID.randomUUID().toString();

    public static final String TEST_FILE_NAME = "test-file.docx";
    public static final String EXPECTED_FILE_NAME_TEMPLATE = "path_list_%s.docx";
    public static final String FAKE_DIRECTORY_NAME = "not-a-directory.txt";
    public static final String TEMPLATE_NOT_FOUND_ERROR_MSG = "Template not found for form:";
    public static final String FAKE_DOC_LOCATION = "classpath:fake_path/*.*";

    private TestConstants() {
    }
}
