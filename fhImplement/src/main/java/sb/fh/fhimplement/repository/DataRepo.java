package sb.fh.fhimplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.fh.fhimplement.entity.EntityData;

import java.util.Optional;

public interface DataRepo extends JpaRepository<EntityData, Integer> {
    Optional<EntityData> findByName(String filename);
}
