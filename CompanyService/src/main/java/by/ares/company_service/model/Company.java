package by.ares.company_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
@EntityListeners(AuditingEntityListener.class)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Version
    @Column(name = "version")
    private Long version = 0L;

    @ManyToMany(mappedBy = "companies")
    private Set<Car> cars = new HashSet<>();

    @ManyToMany(mappedBy = "companies")
    private Set<CarDriver> carDrivers = new HashSet<>();

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public void addCar(Car car) {
        this.cars.add(car);
        car.getCompanies().add(this);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
        car.getCompanies().remove(this);
    }

    public void addCarDriver(CarDriver carDriver) {
        this.carDrivers.add(carDriver);
        carDriver.getCompanies().add(this);
    }

    public void removeCarDriver(CarDriver carDriver) {
        this.carDrivers.remove(carDriver);
        carDriver.getCompanies().remove(this);
    }

}
