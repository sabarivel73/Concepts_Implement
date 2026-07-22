package sb.fh.fhimplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.fh.fhimplement.entity.ResourceEntity;

import java.util.Optional;

public interface ResourceRepo extends JpaRepository<ResourceEntity, Integer> {
    Optional<ResourceEntity> findByOriginalName(String originalName);
    void deleteByFilePath(String filePath);
}
