package by.ares.company_service.repository;

import by.ares.company_service.model.CarDriver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDriverRepository extends JpaRepository<CarDriver, Long> {
    @EntityGraph(attributePaths = {"companies"})
    Optional<CarDriver> findById(Long id);
}
