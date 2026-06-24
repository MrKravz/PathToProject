package by.ares.company_service.controller;

import by.ares.company_service.dto.CompanyDto;
import by.ares.company_service.dto.request.CompanyCreationRequest;
import by.ares.company_service.dto.request.UpdateCompanyRequest;
import by.ares.company_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyDto> save(@RequestBody CompanyCreationRequest companyCreationRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.save(companyCreationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@RequestBody UpdateCompanyRequest updateCompanyRequest,
                                             @PathVariable Long id) {
        return ResponseEntity
                .accepted()
                .body(companyService.update(updateCompanyRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
