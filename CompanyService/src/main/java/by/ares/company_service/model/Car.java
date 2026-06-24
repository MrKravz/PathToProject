package by.ares.company_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SoftDelete;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cars")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mark")
    private String mark;

    @Column(name = "resident_number")
    private String residentNumber;

    @Column(name = "mileage")
    private Integer mileage;

    @ManyToMany
    @JoinTable(
            name = "company_cars",
            joinColumns = @JoinColumn(
                    name = "car_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Company> companySet;

}
