package by.ares.path_list_service.client.fallback;

import by.ares.path_list_service.client.CompanyClient;
import by.ares.path_list_service.dto.CompanyDto;
import by.ares.path_list_service.exception.ApiException;
import by.ares.path_list_service.exception.ApiTimeoutException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CompanyClientFallback implements FallbackFactory<CompanyClient> {

    @Override
    public CompanyClient create(Throwable cause) {
        if (cause instanceof ApiException apiException) {
            throw apiException;
        }
        return new CompanyClient() {
            @Override
            public CompanyDto findById(Long id) {
                throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
            }

            @Override
            public List<CompanyDto> findAllById(List<Long> ids) {
                throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
            }
        };
    }
    
}
