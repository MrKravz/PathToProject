package by.ares.document_service.client.fallback;

import by.ares.document_service.client.CompanyClient;
import by.ares.document_service.exception.ApiException;
import by.ares.document_service.exception.ApiTimeoutException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import static by.ares.document_service.util.DocumentServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CompanyClientFallback implements FallbackFactory<CompanyClient> {

    @Override
    public CompanyClient create(Throwable cause) {
        if (cause instanceof ApiException apiException) {
            throw apiException;
        }
        return id -> {
            throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
        };
    }

}
