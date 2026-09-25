package sb.sqs.sqsimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sqs")
public class Controller {
    @Autowired private CreatingQueue queue;
    @Autowired private Producer producer;
    @PostMapping("/SendQueue")
    public ResponseEntity<String> createQueueResponse(@RequestParam String message) {
        producer.sendMessage(message);
        return new ResponseEntity<>("Message Sent", HttpStatus.ACCEPTED);
    }
    @GetMapping("/getQueue")
    public ResponseEntity<String> getQueueResponse(@RequestParam String queueName) {
        return new ResponseEntity<>(queue.getQueue(queueName), HttpStatus.OK);
    }
}
