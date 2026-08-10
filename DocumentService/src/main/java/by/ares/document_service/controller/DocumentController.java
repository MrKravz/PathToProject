package by.ares.document_service.controller;

import by.ares.document_service.dto.FileResponse;
import by.ares.document_service.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> getDocument(@PathVariable UUID id) {
        return ResponseEntity.ok(documentService.findById(id));
    }

}
