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
@Table(name = "companies")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "companySet")
    private Set<Car> cars;

    @ManyToMany(mappedBy = "companySet")
    private Set<CarDriver> carDrivers;

}
