package by.ares.document_service.repository;

import by.ares.document_service.model.PathList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PathListRepository extends MongoRepository<PathList, UUID> {
}
