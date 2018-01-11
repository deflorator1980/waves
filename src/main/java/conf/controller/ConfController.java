package conf.controller;
import conf.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public ResponseEntity<?> showUsers() {
        return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);
    }
}
