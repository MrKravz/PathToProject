package by.ares.path_list_service.service;

import by.ares.path_list_service.dto.CompanyDto;
import by.ares.path_list_service.service.impl.CompanyClientFallbackService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "company-service",
        url = "${feign.client.company.url:}",
        fallback = CompanyClientFallbackService.class
)
public interface CompanyClient {
    CompanyDto findById(Long id);
}
