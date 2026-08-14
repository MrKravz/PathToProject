package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CarDriverClientFallback;
import by.ares.path_list_service.dto.CarDriverDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        contextId = "car-driver-read-client",
        url = "${feign.client.company.url:}",
        fallbackFactory = CarDriverClientFallback.class
)
@Retry(name = "car-driver-read-client")
@Bulkhead(name = "car-driver-read-client")
public interface CarDriverClient {

    @GetMapping("/car_drivers/{id}")
    CarDriverDto findById(@PathVariable Long id);

}
