package by.ares.path_list_service.client.fallback;

import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.exception.ApiException;
import by.ares.path_list_service.exception.ApiTimeoutException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CarDriverClientFallback implements FallbackFactory<CarDriverClient> {

    @Override
    public CarDriverClient create(Throwable cause) {
        if (cause instanceof ApiException apiException) {
            throw apiException;
        }
        return id -> {
            throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
        };
    }

}
