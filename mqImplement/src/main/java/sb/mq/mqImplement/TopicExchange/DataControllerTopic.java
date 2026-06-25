package sb.mq.mqImplement.TopicExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class DataControllerTopic {
    @Autowired private DataServiceTopic dataServiceTopic;
    @PostMapping("post_2")
    public ResponseEntity<String> post(@RequestParam String name, @RequestParam String email) {
        dataServiceTopic.fun(name, email);
        IO.println("Topic Data List : ");
        return new ResponseEntity<>("Topic Data sent to the queues", HttpStatus.OK);
    }
}
