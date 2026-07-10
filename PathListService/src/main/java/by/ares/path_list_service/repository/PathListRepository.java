package by.ares.path_list_service.repository;

import by.ares.path_list_service.model.PathList;
import by.ares.path_list_service.model.Seria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PathListRepository extends JpaRepository<PathList, UUID> {
    Page<PathList> findAllBySeria(Seria seria, Pageable pageable);

    Page<PathList> findAllByReclamationDateBetween(LocalDate reclamationDateAfter,
                                                   LocalDate reclamationDateBefore, Pageable pageable);

}
