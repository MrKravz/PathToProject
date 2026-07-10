package by.ares.path_list_service.repository;

import by.ares.path_list_service.model.Seria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriaRepository extends JpaRepository<Seria, Integer> {
    Optional<Seria> findByName(String name);
}
