package by.ares.path_list_service.controller;

import by.ares.path_list_service.dto.PathListDto;
import by.ares.path_list_service.dto.SeriaDto;
import by.ares.path_list_service.dto.request.PathListCreationRequest;
import by.ares.path_list_service.service.PathListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/path_lists")
public class PathListController {

    private final PathListService pathListService;

    @GetMapping("/filter_by/seria")
    public ResponseEntity<Page<PathListDto>> findAll(SeriaDto seriaDto, Pageable pageable) {
        return ResponseEntity
                .ok(pathListService.findAllBySeria(seriaDto, pageable));
    }

    @GetMapping("filter_by/date")
    public ResponseEntity<Page<PathListDto>> findAll(LocalDate start, LocalDate end, Pageable pageable) {
        return ResponseEntity
                .ok(pathListService.findAllByCreationDateRange(start, end, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PathListDto> findById(@PathVariable UUID uuid) {
        return ResponseEntity
                .ok(pathListService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<PathListDto> save(@RequestBody PathListCreationRequest pathListCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pathListService.save(pathListCreationRequest));
    }

}
