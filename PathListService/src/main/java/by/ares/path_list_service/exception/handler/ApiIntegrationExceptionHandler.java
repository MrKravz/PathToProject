package by.ares.path_list_service.exception.handler;

import by.ares.path_list_service.dto.ExceptionResponse;
import by.ares.path_list_service.exception.ApiException;
import by.ares.path_list_service.exception.ApiTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiIntegrationExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(ApiException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }

    @ExceptionHandler(value = ApiTimeoutException.class)
    public ResponseEntity<ExceptionResponse> handleApiTimeoutException(ApiTimeoutException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }
    
}
