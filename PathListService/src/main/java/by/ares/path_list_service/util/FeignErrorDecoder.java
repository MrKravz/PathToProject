package by.ares.path_list_service.util;

import by.ares.path_list_service.dto.ExceptionResponse;
import by.ares.path_list_service.exception.ApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;
    private final ErrorDecoder defaultDecoder = new Default();

    public FeignErrorDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Exception decode(String s, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            ExceptionResponse exceptionResponse = mapper.readValue(bodyIs, ExceptionResponse.class);
            return new ApiException(exceptionResponse.getMessage(),
                    HttpStatus.valueOf(response.status()));
        }
        catch (Exception e) {
            return defaultDecoder.decode(s, response);
        }
    }

}
