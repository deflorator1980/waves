package conference;


import conference.model.*;
import conference.repository.RoleRepository;
import conference.repository.RoomRepository;
import conference.repository.UserRepository;
import conference.repository.PresentationRepository;
import conference.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        User userA = new User("UserA");
//        UserServiceImpl userService = new UserServiceImpl();
//        String pass = userService.getPasswd("a");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userA.setPasswd(bCryptPasswordEncoder.encode("a"));
        userA.setRole(2);

        User userB = new User("UserB");
        userB.setPasswd(bCryptPasswordEncoder.encode("b"));
        userB.setRole(2);

        User admin = new User("Administrator");
        admin.setPasswd(bCryptPasswordEncoder.encode("a"));
        admin.setRole(1);

        Presentation presentationA = new Presentation("PresentationOne");
        Presentation presentationB = new Presentation("PresentationTwo");
        Presentation presentationC = new Presentation("PresentationThree");

        Schedule schedule = new Schedule();
        schedule.setUser(userA);
        schedule.setPresentation(presentationA);
        schedule.setDate(new Date());
        schedule.setRoom(4);
        userA.getSchedules().add(schedule);

        Schedule schedule2 = new Schedule();
        schedule2.setUser(userA);
        schedule2.setPresentation(presentationB);
        schedule2.setDate(new Date());
        schedule2.setRoom(1);
        userA.getSchedules().add(schedule2);

        Schedule schedule1 = new Schedule();
        schedule1.setUser(userB);
        schedule1.setPresentation(presentationA);
        schedule1.setDate(new Date());
        schedule1.setRoom(2);
        userB.getSchedules().add(schedule1);

        Schedule schedule3 = new Schedule();
        schedule3.setUser(userB);
        schedule3.setPresentation(presentationC);
        schedule3.setDate(new Date());
        schedule3.setRoom(4);
        userB.getSchedules().add(schedule3);

        roomRepository.save(new Room(1, "One"));
        roomRepository.save(new Room(2, "Two"));
        roomRepository.save(new Room(3, "Three"));
        roomRepository.save(new Room(4, "Four"));

        roleRepository.save(new Role(1, "Administrator"));
        roleRepository.save(new Role(2, "Presenter"));
        roleRepository.save(new Role(3, "Listener"));

        presentationRepository.save(presentationA);
        presentationRepository.save(presentationB);
        presentationRepository.save(presentationC);
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(admin);

    }
}
