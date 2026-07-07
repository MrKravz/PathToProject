package by.ares.company_service.controller;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;
import by.ares.company_service.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> findAllById(@RequestParam @NotEmpty(message = "The list of IDs cannot be empty")
                                                        List<@Min(1) Long> ids) {
        return ResponseEntity.ok(companyService.findAllById(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyDto> save(@Valid @RequestBody CompanyCreationRequest companyCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.save(companyCreationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@Valid @RequestBody UpdateCompanyRequest updateCompanyRequest,
                                             @PathVariable @Min(1) Long id) {
        return ResponseEntity
                .accepted()
                .body(companyService.update(updateCompanyRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        companyService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
