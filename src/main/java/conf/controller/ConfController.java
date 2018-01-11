package conf.controller;
import conf.model.Room;
import conf.model.Schedule;
import conf.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ScheduleRepository scheduleRepository;

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

    @RequestMapping("/schedule")
    public ResponseEntity<?> showSchedule() {

        List<Schedule> schedules = new ArrayList<>();

        for (Room room : roomRepository.findAll()) {
            for (Schedule schedule : scheduleRepository.findByRoomId(room.getId())){
                schedules.add(schedule);
            }
        }

        return new ResponseEntity<Object>(schedules, HttpStatus.OK);
    }

}
