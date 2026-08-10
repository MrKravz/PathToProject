package by.ares.document_service.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum DocumentForm {

    FORM_4P("4-П"),
    FORM_4S("4-С"),
    FORM_6("6"),
    FORM_1("1");

    private final String formName;

    DocumentForm(String formName) {
        this.formName = formName;
    }

    private static final Map<String, DocumentForm> LOOKUP_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(DocumentForm::getFormName, Function.identity()));

    public static DocumentForm fromString(String formName) {
        return LOOKUP_MAP.get(formName);
    }
}
