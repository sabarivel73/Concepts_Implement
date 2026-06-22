package sb.cache.cacheImplement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sb.cache.cacheImplement.entity.User_Table;
import sb.cache.cacheImplement.repository.UserRepo;

import static java.lang.Thread.sleep;

@Service
public class UserService {
    @Autowired private UserRepo userRepo;
    public Integer save(String name, String email) {
        User_Table userTable = new User_Table();
        userTable.setName(name);
        userTable.setEmail(email);
        return userRepo.save(userTable).getId();
    }
    @Cacheable(
            value = "User_Data",
            key = "#id",
            condition = "#id > 0",
            sync = true
    )
    public User_Table get(Integer id) throws InterruptedException {
        sleep(1000);
        return userRepo.findById(id).orElse(null);
    }
    @CachePut(value = "User_Data", key = "#id")
    public User_Table update(Integer id, String email) {
        User_Table value = userRepo.findById(id).orElse(null);
        if(value==null) return null;
        value.setEmail(email);
        return userRepo.save(value);
    }
    @CacheEvict(value = "User_Data", key = "#id")
    public void delete(Integer id) {
        userRepo.deleteById(id);
    }
    @CacheEvict(value = "User_Data", allEntries = true)
    @Scheduled(fixedRate = 10000)
    public void deleteAll() {}
}
