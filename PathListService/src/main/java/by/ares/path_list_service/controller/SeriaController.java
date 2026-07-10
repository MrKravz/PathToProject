package by.ares.path_list_service.controller;

import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.SeriaCreationRequest;
import by.ares.path_list_service.service.SeriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/serias")
public class SeriaController {

    private final SeriaService seriaService;

    @GetMapping
    public ResponseEntity<Set<SeriaDto>> findAll() {
        return ResponseEntity
                .ok(seriaService.findAll());
    }

    @GetMapping("/{seriaName}")
    public ResponseEntity<SeriaDto> findByName(@PathVariable String seriaName) {
        return ResponseEntity
                .ok(seriaService.findByName(seriaName));
    }

    @PostMapping
    public ResponseEntity<SeriaDto> save(@RequestBody SeriaCreationRequest seriaCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(seriaService.save(seriaCreationRequest));
    }

}
