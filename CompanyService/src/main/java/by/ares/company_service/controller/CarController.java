package by.ares.company_service.controller;

import by.ares.company_service.dto.CarDto;
import by.ares.company_service.dto.request.CarCreationRequest;
import by.ares.company_service.dto.request.UpdateCarRequest;
import by.ares.company_service.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> save(@Valid @RequestBody CarCreationRequest carCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carService.save(carCreationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> update(@Valid @RequestBody UpdateCarRequest updateCarRequest,
                                         @PathVariable @Min(1) Long id) {
        return ResponseEntity
                .accepted()
                .body(carService.update(updateCarRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        carService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
