package by.ares.company_service.exception.handler;

import by.ares.company_service.dto.ExceptionResponse;
import by.ares.company_service.exception.CarDriverNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CarDriverExceptionHandler {
    @ExceptionHandler(value = CarDriverNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCarDriverNotFoundException(CarDriverNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }
}
