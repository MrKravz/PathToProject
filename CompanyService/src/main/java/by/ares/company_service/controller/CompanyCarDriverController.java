package by.ares.company_service.controller;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.service.CompanyCarDriverService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/car_drivers")
public class CompanyCarDriverController {

    private final CompanyCarDriverService companyCarDriverService;

    @PutMapping("/{carDriverId}")
    public ResponseEntity<CompanyDto> assignCar(@PathVariable @Min(1) Long companyId,
                                                @PathVariable @Min(1) Long carDriverId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(companyCarDriverService.assign(companyId, carDriverId));
    }

    @DeleteMapping("/{carDriverId}")
    public ResponseEntity<CompanyDto> unassignCar(@PathVariable @Min(1) Long companyId,
                                                  @PathVariable @Min(1) Long carDriverId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(companyCarDriverService.unassign(companyId, carDriverId));
    }

}
