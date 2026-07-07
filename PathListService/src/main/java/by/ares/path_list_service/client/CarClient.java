package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CompanyClientFallback;
import by.ares.path_list_service.dto.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        contextId = "car-read-client",
        url = "${feign.client.company.url:}",
        fallback = CompanyClientFallback.class
)
public interface CarClient {

    @GetMapping("/cars/{id}")
    CarDto findById(@PathVariable Long id);

}
