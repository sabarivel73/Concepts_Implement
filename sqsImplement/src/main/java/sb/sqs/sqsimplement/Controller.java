package sb.sqs.sqsimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sqs")
public class Controller {
    @Autowired private CreatingQueue queue;
    @PostMapping("/createQueue")
    public ResponseEntity<String> createQueueResponse(@RequestParam String queueName) {
        return new ResponseEntity<>(queue.queueUrl(queueName), HttpStatus.CREATED);
    }
    @GetMapping("/getQueue")
    public ResponseEntity<String> getQueueResponse(@RequestParam String queueName) {
        return new ResponseEntity<>(queue.getQueue(queueName), HttpStatus.OK);
    }
}
