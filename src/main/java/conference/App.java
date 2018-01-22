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
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        User userA = new User("UserA");
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

        Room room1 = new Room("One");
        Room room2 = new Room("Two");
        Room room3 = new Room("Three");
        Room room4 = new Room("Four");

        Schedule schedule = new Schedule();
        schedule.setUser(userA);
        schedule.setPresentation(presentationA);
        schedule.setDate(new Date());
        schedule.setRoom(room4);
        userA.getSchedules().add(schedule);
        room4.getSchedules().add(schedule);

        Schedule schedule2 = new Schedule();
        schedule2.setUser(userA);
        schedule2.setPresentation(presentationB);
        schedule2.setDate(new Date());
        schedule2.setRoom(room1);
        userA.getSchedules().add(schedule2);
        room1.getSchedules().add(schedule2);

        Schedule schedule1 = new Schedule();
        schedule1.setUser(userB);
        schedule1.setPresentation(presentationA);
        schedule1.setDate(new Date());
        schedule1.setRoom(room2);
        userB.getSchedules().add(schedule1);
        room2.getSchedules().add(schedule1);

        Schedule schedule3 = new Schedule();
        schedule3.setUser(userB);
        schedule3.setPresentation(presentationC);
        Date date = new Date();
        schedule3.setDate(new Date(date.getTime() + TimeUnit.HOURS.toMillis(1)));
        schedule3.setRoom(room4);
        userB.getSchedules().add(schedule3);
        room4.getSchedules().add(schedule3);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);

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
