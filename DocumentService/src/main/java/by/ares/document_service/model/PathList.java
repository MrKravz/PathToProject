package by.ares.document_service.model;

import by.ares.document_service.dto.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Getter
@Setter
@Document
@Accessors(chain = true)
public class PathList {

    @Id
    private String id;

    @Field(name = "number")
    private Integer number;

    @Field(name = "car")
    private CarDto car;

    @Field(name = "car_driver")
    private CarDriverDto carDriver;

    @Field(name = "company")
    private CompanyDto company;

    @Field(name = "reclamation_date")
    private LocalDate reclamationDate;

    @Field(name = "route")
    private RouteDto route;

    @Field(name = "seria")
    private SeriaDto seria;

}
