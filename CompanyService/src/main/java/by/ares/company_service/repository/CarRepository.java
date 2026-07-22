package by.ares.company_service.repository;

import by.ares.company_service.model.Car;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @EntityGraph(attributePaths = {"companies"})
    Optional<Car> findById(Long id);
}
