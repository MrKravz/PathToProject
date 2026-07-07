package by.ares.path_list_service.client.fallback;

import by.ares.path_list_service.client.CarClient;
import by.ares.path_list_service.dto.CarDto;
import by.ares.path_list_service.exception.ApiTimeoutException;
import org.springframework.stereotype.Component;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CarClientFallback implements CarClient {
    @Override
    public CarDto findById(Long id) {
        throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
    }
}
