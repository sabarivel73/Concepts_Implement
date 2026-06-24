package sb.mq.mqImplement.FanoutExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq_1")
public class DataControllerFanout {
    @Autowired private DataServiceFanout dataService;
    @PostMapping("/post_1")
    public ResponseEntity<String> post(@RequestParam String name, @RequestParam String email) {
        dataService.fun(name, email);
        IO.println("Fanout Data List : ");
        return new ResponseEntity<>("Data sent to fanout queues", HttpStatus.OK);
    }
}
