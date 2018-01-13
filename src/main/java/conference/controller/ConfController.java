package conference.controller;

import conference.model.*;
import conference.repository.RoleRepository;
import conference.repository.RoomRepository;
import conference.repository.UserRepository;
import conference.repository.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ConfController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/schedule")
    public ResponseEntity<?> showSchedule() {
        List<ScheduleRest> scheduleRestList = new ArrayList<>();
//        ScheduleRest scheduleRest = new ScheduleRest();

        List<Schedule> schedules = new ArrayList<>();
        List<User> users = (List<User>) userRepository.findAll();
        for (User user : users) {
            schedules.addAll(user.getSchedules());
        }

        schedules.sort(Comparator.comparing(a -> a.getRoom()));
        for (Schedule schedule : schedules) {
            ScheduleRest scheduleRest = new ScheduleRest();
            scheduleRest.setPresentationDate(schedule.getDate());
            scheduleRest.setPresentationName(schedule.getPresentation().getName());
            scheduleRest.setUserName(schedule.getUser().getName());
            scheduleRest.setRoomName(roomRepository.findOne(schedule.getRoom()).getName());
            scheduleRestList.add(scheduleRest);
        }

        return new ResponseEntity<Object>(scheduleRestList, HttpStatus.OK);
    }

    @RequestMapping("/user_presentations")
    public ResponseEntity<?> showUserPresentations() {
        int userId = 3;
        List<ScheduleRest> scheduleRestList = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        schedules.addAll(userRepository.findOne(userId).getSchedules());

        for (Schedule schedule : schedules) {
            ScheduleRest scheduleRest = new ScheduleRest();
            scheduleRest.setPresentationDate(schedule.getDate());
            scheduleRest.setPresentationName(schedule.getPresentation().getName());
            scheduleRest.setUserName(schedule.getUser().getName());
            scheduleRest.setRoomName(roomRepository.findOne(schedule.getRoom()).getName());
            scheduleRestList.add(scheduleRest);
        }

        return new ResponseEntity<Object>(scheduleRestList, HttpStatus.OK);
    }

    @RequestMapping("/presentations")
    public ResponseEntity<?> showPublishers() {
        return new ResponseEntity<Object>(presentationRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/users")
    public ResponseEntity<?> showUsers() {
        List<UserRest> userRestList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserRest userRest = new UserRest();
            userRest.setId(user.getId());
            userRest.setName(user.getName());
            userRest.setRole(roleRepository.findOne(user.getRole()).getName());
            userRestList.add(userRest);
        }
        return new ResponseEntity<Object>(userRestList, HttpStatus.OK);
//        return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> updateUsers(@RequestBody List<UserRest> userRestList) {
        for (UserRest userRest : userRestList) {
            User user = userRepository.findOne(userRest.getId());
            int roleId = roleRepository.findRoleByName("Administrator").getId();
            user.setRole(roleId);
            user.setName(userRest.getName());
            userRepository.save(user);
        }
        return new ResponseEntity<Object>(userRestList, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestBody UserRest userRest) {
        if (userRepository.findOne(userRest.getId()) == null) {
            userRest.setRole("NOT FOUND");
            return new ResponseEntity<Object>(userRest, HttpStatus.OK);
        }
        userRepository.delete(userRest.getId());
        userRest.setRole("DELETED");
        return new ResponseEntity<Object>(userRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody UserSignup userSignup) {
        if (userRepository.findUserByName(userSignup.getName()) != null) {
            userSignup.setPasswd("already exists");
            return new ResponseEntity<Object>(userSignup, HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setRole(3);
        user.setPasswd(userSignup.getPasswd());
        user.setName(userSignup.getName());
        userRepository.save(user);
        userSignup.setPasswd("OK");
        return new ResponseEntity<Object>(userSignup, HttpStatus.OK);
    }

}
