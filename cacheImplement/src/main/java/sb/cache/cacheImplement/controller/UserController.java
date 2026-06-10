package sb.cache.cacheImplement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sb.cache.cacheImplement.entity.User_Table;
import sb.cache.cacheImplement.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired private UserService userService;
    @PostMapping("/post")
    public ResponseEntity<Integer> save(@RequestParam String name, @RequestParam String email) {
        return new ResponseEntity<>(userService.save(name, email), HttpStatus.OK);
    }
    @GetMapping("/get")
    public ResponseEntity<User_Table> get(@RequestParam Integer id) throws InterruptedException {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }
}
