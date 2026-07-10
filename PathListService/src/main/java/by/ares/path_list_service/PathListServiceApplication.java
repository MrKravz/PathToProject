package by.ares.path_list_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class PathListServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PathListServiceApplication.class, args);
    }

}
