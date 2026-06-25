package sb.mq.mqImplement.AckNack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class DataControllerAN {
    @Autowired private DataServiceAN dataServiceAN;
    @PostMapping("/post_AN")
    public ResponseEntity<String> post(@RequestParam String name, @RequestParam String email) {
        dataServiceAN.fun(name, email);
        return new ResponseEntity<>("AN Data is sent to a queue", HttpStatus.OK);
    }
}
