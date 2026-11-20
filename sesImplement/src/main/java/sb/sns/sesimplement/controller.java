package sb.sns.sesimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ses")
public class controller {

    @Autowired
    private service service;

    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestParam String from, @RequestParam String to, @RequestParam String content) {
        return new ResponseEntity<>(service.sendEmail(from, to, content), HttpStatus.CREATED);
    }

}
