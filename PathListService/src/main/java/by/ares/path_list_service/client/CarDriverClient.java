package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CarDriverClientFallback;
import by.ares.path_list_service.dto.CarDriverDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        contextId = "car-driver-read-client",
        url = "${feign.client.company.url:}",
        fallback = CarDriverClientFallback.class
)
public interface CarDriverClient {

    @GetMapping("/car_drivers/{id}")
    CarDriverDto findById(@PathVariable Long id);

}
