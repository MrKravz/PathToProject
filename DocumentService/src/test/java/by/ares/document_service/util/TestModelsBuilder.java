package by.ares.document_service.util;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.dto.PathListDto;
import by.ares.document_service.model.DocumentForm;
import by.ares.document_service.model.PathList;

import static by.ares.document_service.util.TestConstants.EXISTING_PATH_LIST_ID;

public class TestModelsBuilder {

    public static PathListDto buildPathListDto() {
        return PathListDto.builder()
                .id(EXISTING_PATH_LIST_ID)
                .documentForm(DocumentForm.FORM_4P)
                .build();
    }

    public static PathList buildPathList() {
        return PathList.builder()
                .build();
    }
    public static FileResponse buildFileResponse() {
        return FileResponse.builder().build();
    }


    private TestModelsBuilder() {}

}
