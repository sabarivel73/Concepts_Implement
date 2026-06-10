package sb.cache.cacheImplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sb.cache.cacheImplement.entity.User_Table;

@Repository
public interface UserRepo extends JpaRepository<User_Table, Integer> {
}
