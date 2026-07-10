package by.ares.path_list_service.exception;

public class ApiTimeoutException extends RuntimeException {
    public ApiTimeoutException(String message) {
        super(message);
    }
}
