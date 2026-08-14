package by.ares.document_service.client;

import by.ares.document_service.client.fallback.CompanyClientFallback;
import by.ares.document_service.dto.CompanyDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service",
        contextId = "company-read-client",
        url = "${feign.client.company.url:}",
        fallbackFactory = CompanyClientFallback.class
)
@Retry(name = "company-read-client")
@Bulkhead(name = "company-read-client")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDto findById(@PathVariable Long id);

}
