package conference;


import conference.model.*;
import conference.repository.RoleRepository;
import conference.repository.RoomRepository;
import conference.repository.UserRepository;
import conference.repository.PresentationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Date;

@SpringBootApplication
public class App implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        User userA = new User("User1");
        userA.setPasswd("a");
        userA.setRole(1);

        User userB = new User("User2");
        userB.setPasswd("b");
        userB.setRole(2);

        Presentation presentationA = new Presentation("PresentationOne");
        Presentation presentationB = new Presentation("PresentationTwo");

        Schedule schedule = new Schedule();
        schedule.setUser(userA);
        schedule.setPresentation(presentationA);
        schedule.setDate(new Date());
        schedule.setRoom(4);
        userA.getSchedules().add(schedule);

        Schedule schedule1 = new Schedule();
        schedule1.setUser(userB);
        schedule1.setPresentation(presentationA);
        schedule1.setDate(new Date());
        schedule1.setRoom(2);
        userB.getSchedules().add(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setUser(userA);
        schedule2.setPresentation(presentationB);
        schedule2.setDate(new Date());
        schedule2.setRoom(1);
        userA.getSchedules().add(schedule2);

        presentationRepository.save(presentationA);
        presentationRepository.save(presentationB);
        userRepository.save(userA);
        userRepository.save(userB);
//        roomRepository.save(new Room("One"));
//        roomRepository.save(new Room("Two"));
        roomRepository.save(new Room(1, "One"));
        roomRepository.save(new Room(2, "Two"));
        roomRepository.save(new Room(4, "Four"));

        roleRepository.save(new Role(1, "Administrator"));
        roleRepository.save(new Role(2, "Presenter"));
        roleRepository.save(new Role(3, "Listener"));

        // test
        int roleId = roleRepository.findRoleByName("Administrator").getId();
        System.out.println("role id=" + roleId);
        User user = new User();
        user.setRole(roleId);
        System.out.println(user);
        // update
//        bookA.getSchedules().remove(schedule);
//        bookRepository.save(bookA);

        // test
    }
}
