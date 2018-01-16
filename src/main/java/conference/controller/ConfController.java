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
        int userId = 4;  // Postgres
//        int userId = 1;  // H2     todo
        Set<ScheduleRest> scheduleRestList = new HashSet<>();
        Set<Schedule> schedules = new HashSet<>();
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

    @RequestMapping(value = "/user_presentation", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserPresentation(@RequestBody Presentation presentation) {
//        int userId = 1;     //H2
        int userId = 4;     //Postgres todo
        Schedule scheduleDel = new Schedule();
        User user = userRepository.findOne(userId);
        Set<Schedule> scheduleSet = user.getSchedules();
        System.out.println(scheduleSet);
        for (Schedule schedule : scheduleSet) {
            if(schedule.getPresentation().getId() == presentation.getId()){
                scheduleDel = schedule;
            }
        }
        if(scheduleDel.getUser() == null) {
            return new ResponseEntity<Object>(new Role(1, "NOT FOUTND"), HttpStatus.NOT_FOUND);
        }
        scheduleSet.remove(scheduleDel);
        userRepository.save(user);
        return new ResponseEntity<Object>(scheduleSet, HttpStatus.OK);
    }

    /**
     * изменение времени, места доклада в расписании
     *
     * update schedule set date=?, room=? where user_id=? and presentation_id=?
     * @param scheduleRestList
     * @return
     */
    @RequestMapping(value = "/user_presentations", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserPresentations(@RequestBody List<ScheduleRest> scheduleRestList) {
        int userId = 4;  // Postgres
//        int userId = 1;   //H2  todo
        User user = userRepository.findOne(userId);
        Set<Schedule> schedulesSet = user.getSchedules();
        Set<Schedule> schedulesUpdatedSet = new HashSet<>();
        List<Presentation> presentationList = new ArrayList<>();

        for (Schedule schedule : schedulesSet) {
            for( ScheduleRest scheduleRest : scheduleRestList) {
                Presentation presentation = new Presentation();
                presentation = presentationRepository.findPresentationByName(scheduleRest.getPresentationName());
                if (schedule.getPresentation().getId() == presentation.getId()) {
                    schedule.setDate(scheduleRest.getPresentationDate());
                    schedule.setRoom(roomRepository.findRoomByName(scheduleRest.getRoomName()).getId());
                    schedulesUpdatedSet.add(schedule);
                }
            }
        }
        schedulesSet.removeAll(schedulesUpdatedSet);
        schedulesSet.addAll(schedulesUpdatedSet);
        userRepository.save(user);
        return new ResponseEntity<Object>(schedulesUpdatedSet, HttpStatus.OK);

    }


    @RequestMapping(value = "/user_presentations", method = RequestMethod.POST)
    public ResponseEntity<?> createUserPresentations(@RequestBody ScheduleRest scheduleRest) {
        int userId = 4;    // Postgres todo
//        int userId = 1;    // H2
        User user = userRepository.findOne(userId);

        Presentation presentation = new Presentation(scheduleRest.getPresentationName());

        Schedule scheduleN = new Schedule();
        scheduleN.setUser(user);
//        scheduleN.setPresentation(presentationRepository.findOne(3));
        scheduleN.setPresentation(presentation);
        scheduleN.setDate(scheduleRest.getPresentationDate());
        scheduleN.setRoom(roomRepository.findRoomByName(scheduleRest.getRoomName()).getId());
        user.getSchedules().add(scheduleN);

        presentationRepository.save(presentation);
//        userRepository.save(userRepository.findOne(2));    // not this user junky Hibernate

        return new ResponseEntity<Object>(userRepository.findOne(user.getId()), HttpStatus.OK);
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
            int roleId = roleRepository.findRoleByName(userRest.getRole()).getId();
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
            return new ResponseEntity<Object>(userRest, HttpStatus.NOT_FOUND);
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
