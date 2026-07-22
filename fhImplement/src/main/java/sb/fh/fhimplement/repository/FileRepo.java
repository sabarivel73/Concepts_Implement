package sb.fh.fhimplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.fh.fhimplement.entity.FileEntity;

import java.util.Optional;

public interface FileRepo extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByFileName(String fileName);
    void deleteByFileKey(String key);
}
