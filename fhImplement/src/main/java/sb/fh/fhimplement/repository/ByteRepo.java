package sb.fh.fhimplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.fh.fhimplement.entity.ByteEntity;

import java.util.Optional;

public interface ByteRepo extends JpaRepository<ByteEntity, Integer> {
    Optional<ByteEntity> findByName(String filename);
    void deleteByName(String filename);
}
