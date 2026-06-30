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
@Table(name = "cars")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
@EntityListeners(AuditingEntityListener.class)
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
    private Set<Company> companies = new HashSet<>();

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

}
