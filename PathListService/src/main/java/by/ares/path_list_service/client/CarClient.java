package by.ares.path_list_service.client;

import by.ares.path_list_service.client.fallback.CarClientFallback;
import by.ares.path_list_service.dto.CarDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        contextId = "car-read-client",
        url = "${feign.client.company.url:}",
        fallbackFactory = CarClientFallback.class
)
@Retry(name = "car-read-client")
@Bulkhead(name = "car-read-client")
public interface CarClient {

    @GetMapping("/cars/{id}")
    CarDto findById(@PathVariable Long id);

}
