package by.ares.path_list_service.exception.handler;

import by.ares.path_list_service.dto.ExceptionResponse;
import by.ares.path_list_service.exception.PathListNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PathListExceptionHandler {

    @ExceptionHandler(value = PathListNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePathListNotFoundException(PathListNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }


}
