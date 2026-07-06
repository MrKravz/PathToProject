package by.ares.path_list_service.service.impl;

import by.ares.path_list_service.dto.CompanyDto;
import by.ares.path_list_service.exception.ApiTimeoutException;
import by.ares.path_list_service.service.CompanyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static by.ares.path_list_service.util.PathListServiceConstants.API_TIMEOUT_MESSAGE;

@Slf4j
@Service
public class CompanyClientFallbackService implements CompanyClient {

    @Override
    public CompanyDto findById(Long id) {
        throw new ApiTimeoutException(API_TIMEOUT_MESSAGE);
    }

}
