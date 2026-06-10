package sb.cache.cacheImplement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
            unless = "#result.email == 'user@gmail.com'",
            sync = true
    )
    public User_Table get(Integer id) throws InterruptedException {
        sleep(1000);
        return userRepo.findById(id).orElse(null);
    }
}
