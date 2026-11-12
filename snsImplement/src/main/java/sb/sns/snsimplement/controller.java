package sb.sns.snsimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sns")
public class controller {
    @Autowired
    private SNSPublisher publisher;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam String message) {
        return new ResponseEntity<>("Message ID: " + publisher.publish(message), HttpStatus.OK);
    }
}
