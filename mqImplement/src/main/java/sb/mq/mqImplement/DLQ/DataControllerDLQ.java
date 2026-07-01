package sb.mq.mqImplement.DLQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class DataControllerDLQ {
    @Autowired
    private DataServiceDLQ service;
    @PostMapping("/post_DLQ")
    public ResponseEntity<String> fun(@RequestParam String name, @RequestParam String email) {
        service.fun(name, email);
        return new ResponseEntity<>("Data sent to Normal Queue", HttpStatus.OK);
    }
}
