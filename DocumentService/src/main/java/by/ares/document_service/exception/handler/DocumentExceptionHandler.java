package by.ares.document_service.exception.handler;

import by.ares.document_service.dto.ExceptionResponse;
import by.ares.document_service.exception.DocumentCreationException;
import by.ares.document_service.exception.PathListNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DocumentExceptionHandler {

    @ExceptionHandler(value = PathListNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePathListNotFoundException(PathListNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }

    @ExceptionHandler(value = DocumentCreationException.class)
    public ResponseEntity<ExceptionResponse> handleDocumentCreationException(DocumentCreationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse(ex.getMessage(), System.currentTimeMillis()));
    }
}
