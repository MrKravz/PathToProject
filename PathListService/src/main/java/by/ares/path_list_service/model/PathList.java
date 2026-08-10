package by.ares.path_list_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "path_lists")
@Accessors(chain = true)
@SoftDelete(columnName = "deleted")
@EntityListeners(AuditingEntityListener.class)
public class PathList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "car_id")
    private Long carId;

    @Column(name = "car_driver_id")
    private Long carDriverId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "reclamation_date")
    private LocalDate reclamationDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_form")
    private DocumentForm documentForm;

    @OneToOne
    @JoinColumn(
            name = "route_id",
            referencedColumnName = "id"
    )
    private Route route;

    @ManyToOne
    @JoinColumn(
            name = "seria_id",
            referencedColumnName = "id"
    )
    private Seria seria;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
