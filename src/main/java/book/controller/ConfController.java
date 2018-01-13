package book.controller;

import book.repository.BookRepository;
import book.repository.PublisherRepository;
import conf.repos.PresentationRepository;
import conf.repos.RoleRepository;
import conf.repos.RoomRepository;
import conf.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class ConfController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PresentationRepository presentationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

//    @RequestMapping("/users")
//    public ResponseEntity<?> showUsers() {
//        return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping("/rooms")
//    public ResponseEntity<?> showRooms() {
//        return new ResponseEntity<Object>(roomRepository.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping("/roles")
//    public ResponseEntity<?> showRoles() {
//        return new ResponseEntity<Object>(roleRepository.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping("/presentations")
//    public ResponseEntity<?> showPresentations() {
//        return new ResponseEntity<Object>(presentationRepository.findAll(), HttpStatus.OK);
//    }

    @RequestMapping("/books")
    public ResponseEntity<?> showBooks() {
        return new ResponseEntity<Object>(bookRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/publishers")
    public ResponseEntity<?> showPublishers() {
        return new ResponseEntity<Object>(publisherRepository.findAll(), HttpStatus.OK);
    }

}
