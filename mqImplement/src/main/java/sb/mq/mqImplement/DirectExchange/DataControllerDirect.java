package sb.mq.mqImplement.DirectExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class DataControllerDirect {
    @Autowired private DataServiceDirect service;
    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam String name, @RequestParam String email) {
        Integer id = (int) (Math.random()*10000);
        service.fun(id, name, email);
        IO.println("Data List : ");
        return new ResponseEntity<>("Data sent to queue", HttpStatus.OK);
    }
}
