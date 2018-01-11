package conf.controller;
import conf.repos.PresentationRepository;
import conf.repos.RoleRepository;
import conf.repos.RoomRepository;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PresentationRepository presentationRepository;

    @RequestMapping("/users")
    public ResponseEntity<?> showUsers() {
        return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/rooms")
    public ResponseEntity<?> showRooms() {
        return new ResponseEntity<Object>(roomRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/roles")
    public ResponseEntity<?> showRoles() {
        return new ResponseEntity<Object>(roleRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/presentations")
    public ResponseEntity<?> showPresentations() {
        return new ResponseEntity<Object>(presentationRepository.findAll(), HttpStatus.OK);
    }

}
