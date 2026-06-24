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
@Table(name = "car_drivers")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
public class CarDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "driver_license_number")
    private String driverLicenseNumber;

    @ManyToMany
    @JoinTable(
            name = "company_drivers",
            joinColumns = @JoinColumn(
                    name = "driver_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Company> companySet;

}
