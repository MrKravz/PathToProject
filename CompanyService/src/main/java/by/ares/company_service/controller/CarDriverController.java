package by.ares.company_service.controller;

import by.ares.company_service.dto.CarDriverDto;
import by.ares.company_service.dto.request.CarDriverCreationRequest;
import by.ares.company_service.dto.request.UpdateCarDriverRequest;
import by.ares.company_service.service.CarDriverService;
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
@RequestMapping("/car_drivers")
public class CarDriverController {

    private final CarDriverService carDriverService;

    @GetMapping("/{id}")
    public ResponseEntity<CarDriverDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(carDriverService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarDriverDto> save(@Valid @RequestBody CarDriverCreationRequest carDriverCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carDriverService.save(carDriverCreationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDriverDto> update(@Valid @RequestBody UpdateCarDriverRequest updateCarDriverRequest,
                                               @PathVariable @Min(1) Long id) {
        return ResponseEntity
                .accepted()
                .body(carDriverService.update(updateCarDriverRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        carDriverService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
