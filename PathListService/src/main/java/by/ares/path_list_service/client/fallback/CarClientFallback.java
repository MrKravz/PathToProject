package by.ares.path_list_service.client.fallback;

import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.exception.ApiException;
import by.ares.path_list_service.exception.ApiTimeoutException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CarClientFallback implements FallbackFactory<CarClient> {

    @Override
    public CarClient create(Throwable cause) {
        if (cause instanceof ApiException apiException) {
            throw apiException;
        }
        return id -> {
            throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
        };
    }

}
