package by.ares.company_service.repository;

import by.ares.company_service.model.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @EntityGraph(attributePaths = {"cars", "carDrivers"})
    List<Company> findAllById(Iterable<Long> ids);

    @EntityGraph(attributePaths = {"cars", "carDrivers"})
    Optional<Company> findById(Long id);

}
