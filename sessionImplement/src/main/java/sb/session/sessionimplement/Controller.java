package sb.session.sessionimplement;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/session")
public class Controller {

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam String name, @RequestParam String email, HttpSession session) {
        session.setAttribute("name", name);
        session.setAttribute("email", email);
        Roles role = new Roles();
        role.setRoles("Hi "+ name +", Role user is enabled for you");
        session.setAttribute("role", role);
        return new ResponseEntity<>(session.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<HashMap<String, Object>> get(HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", session.getAttribute("name"));
        map.put("email", session.getAttribute("email"));
        map.put("role", session.getAttribute("role"));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> roles(HttpSession session) {
        return new ResponseEntity<>(((Roles) session.getAttribute("role")).getRoles(), HttpStatus.OK);
    }

    @PutMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestParam String role, HttpSession session) {
        Roles roles = (Roles) session.getAttribute("role");
        roles.setRoles("Hi "+ session.getAttribute("name") +", " + role + " user is enabled for you");
        session.setAttribute("role", roles);
        return new ResponseEntity<>(role + " role added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteSession")
    public ResponseEntity<String> deleteSession(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Session deleted successfully", HttpStatus.OK);
    }

}
