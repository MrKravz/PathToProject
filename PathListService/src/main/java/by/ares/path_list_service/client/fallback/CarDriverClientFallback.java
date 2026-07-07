package by.ares.path_list_service.client.fallback;

import by.ares.path_list_service.client.CarDriverClient;
import by.ares.path_list_service.dto.CarDriverDto;
import by.ares.path_list_service.exception.ApiTimeoutException;
import org.springframework.stereotype.Component;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Component
public class CarDriverClientFallback implements CarDriverClient {
    @Override
    public CarDriverDto findById(Long id) {
        throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
    }
}
