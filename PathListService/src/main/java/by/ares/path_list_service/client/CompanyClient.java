package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CompanyClientFallback;
import by.ares.path_list_service.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "company-service",
        contextId = "company-read-client",
        url = "${feign.client.company.url:}",
        fallback = CompanyClientFallback.class
)
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDto findById(@PathVariable Long id);

    List<CompanyDto> findAllById(List<Long> ids);
}
