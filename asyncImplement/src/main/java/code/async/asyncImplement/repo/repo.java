package code.async.asyncImplement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import code.async.asyncImplement.entity.db;

@Repository
public interface repo extends JpaRepository<db, Integer> {
}
