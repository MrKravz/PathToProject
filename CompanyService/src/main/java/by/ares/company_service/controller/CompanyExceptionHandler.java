package by.ares.company_service.controller;

import by.ares.company_service.dto.ExceptionResponse;
import by.ares.company_service.exception.CompanyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CompanyExceptionHandler {
    @ExceptionHandler(value = CompanyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePaymentCardNotFoundException(CompanyNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }
}
