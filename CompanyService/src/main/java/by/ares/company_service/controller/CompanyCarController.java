package by.ares.company_service.controller;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.service.CompanyCarService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/cars")
public class CompanyCarController {

    private final CompanyCarService companyCarService;

    @PutMapping("/{carId}")
    public ResponseEntity<CompanyDto> assignCar(@PathVariable @Min(1) Long companyId,
                                                @PathVariable @Min(1) Long carId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(companyCarService.assign(companyId, carId));
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<CompanyDto> unassignCar(@PathVariable @Min(1) Long companyId,
                                                  @PathVariable @Min(1) Long carId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(companyCarService.unassign(companyId, carId));
    }

}
