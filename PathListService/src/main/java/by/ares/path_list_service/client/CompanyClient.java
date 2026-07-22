package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CompanyClientFallback;
import by.ares.path_list_service.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "company-service",
        contextId = "company-read-client",
        url = "${feign.client.company.url:}",
        fallbackFactory = CompanyClientFallback.class
)
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDto findById(@PathVariable Long id);

    @GetMapping("/companies")
    List<CompanyDto> findAllById(@RequestParam("ids") List<Long> ids);

}
