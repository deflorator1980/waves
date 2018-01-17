package conference.controller;

import conference.model.*;
import conference.repository.RoleRepository;
import conference.repository.RoomRepository;
import conference.repository.UserRepository;
import conference.repository.PresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        System.out.println(name);
        User user = userRepository.findUserByName(name);
        int userId = user.getId();

        Set<ScheduleRest> scheduleRestList = new HashSet<>();
        Set<Schedule> schedules = userRepository.findOne(userId).getSchedules();

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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        System.out.println(name);
        User user = userRepository.findUserByName(name);
        Schedule scheduleDel = new Schedule();
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
        return new ResponseEntity<Object>(presentation, HttpStatus.OK);
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        System.out.println(name);
        User user = userRepository.findUserByName(name);
        Set<Schedule> schedulesSet = user.getSchedules();
        Set<Schedule> schedulesUpdatedSet = new HashSet<>();
        List<Presentation> presentationList = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(scheduleRestList.size());
        for (Schedule schedule : schedulesSet) {
            for( ScheduleRest scheduleRest : scheduleRestList) {
                Presentation presentation = presentationRepository.findPresentationByName(scheduleRest.getPresentationName());
                if (presentation == null) {
                    return new ResponseEntity<Object>(new Role(0, "Presentation doesn't exist"), HttpStatus.NOT_FOUND);
                }
                if (schedule.getPresentation().getId() == presentation.getId()) {
                    schedule.setDate(scheduleRest.getPresentationDate());
                    Room room = roomRepository.findRoomByName(scheduleRest.getRoomName());
                    if (room == null) {
                        return new ResponseEntity<Object>(new Role(0, "Room doesn't exist"), HttpStatus.NOT_FOUND);
                    }
                    schedule.setRoom(room.getId());
                    schedulesUpdatedSet.add(schedule);
                    counter.decrementAndGet();
                }
            }
        }
        if (counter.get() != 0) {
            return new ResponseEntity<Object>(new UserRest(0, "Presentation doesn't exist for user", user.getName()), HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        return new ResponseEntity<Object>(scheduleRestList, HttpStatus.OK);
//todo return valid JSON
    }

    /**
     * добавляет новую презентацию
     *
     * @param scheduleRest
     * @return
     */
    @RequestMapping(value = "/user_presentations", method = RequestMethod.POST)
    public ResponseEntity<?> createUserPresentations(@RequestBody ScheduleRest scheduleRest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        System.out.println(name);
        User user = userRepository.findUserByName(name);

        Presentation presentation = new Presentation(scheduleRest.getPresentationName());

        Schedule scheduleN = new Schedule();
        scheduleN.setUser(user);
        scheduleN.setPresentation(presentation);
        scheduleN.setDate(scheduleRest.getPresentationDate());
        Room room = roomRepository.findRoomByName(scheduleRest.getRoomName());
        if (room == null) {
            return new ResponseEntity<Object>(new Role(0, "Room doesn't exist"), HttpStatus.NOT_FOUND);
        }
        scheduleN.setRoom(room.getId());
        user.getSchedules().add(scheduleN);
        //todo schedule already exist
        presentationRepository.save(presentation);
//        userRepository.save(userRepository.findOne(2));    // not this user junky Hibernate

        return new ResponseEntity<Object>(scheduleRest, HttpStatus.OK);
    }

    @RequestMapping("/presentations")
    public ResponseEntity<?> showPublishers() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        System.out.println(name);
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
    }

    /**
     * изменяет роли и имена пользователей
     *
      * @param userRestList
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> updateUsers(@RequestBody List<UserRest> userRestList) {
        for (UserRest userRest : userRestList) {
            User user = userRepository.findOne(userRest.getId());
            if (user == null) {
                return new ResponseEntity<Object>(new Role(0, "USER DOESN'T EXISTS"), HttpStatus.NOT_FOUND);
            }
            Role role = roleRepository.findRoleByName(userRest.getRole());
            if (role == null) {
                return new ResponseEntity<Object>(new Role(0, "ROLE DOESN'T EXISTS"), HttpStatus.NOT_FOUND);
            }
            int roleId = role.getId();
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
            return new ResponseEntity<Object>(userRest, HttpStatus.NOT_FOUND); //todo set by all excetpions userRest
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

    @RequestMapping("/")
    public ResponseEntity<?> welcome() {
        return new ResponseEntity<Object>(new Role(1, "WELCOME"), HttpStatus.OK);
    }

}
