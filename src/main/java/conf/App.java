package conf;

import conf.model.*;
import conf.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class App implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PresentationRepository presentationRepository;



    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Transactional
    @Override
    public void run(String... strings) throws Exception {

//        userRepository.save(new User("Ivan", "Petrow", "a", 1));
//        userRepository.save(new User("Petr", "Ivanow", "a", 1));
//        userRepository.findAll().forEach(u -> log.info(u.toString()));
//
//        roomRepository.save(new Room("one"));
//        roomRepository.save(new Room("two"));
//        roomRepository.findAll().forEach(r -> log.info(r.toString()));
//
//        roleRepository.save(new Role("Admin"));
//        roleRepository.save(new Role("Presenter"));
//        roleRepository.save(new Role("Listener"));
//        roleRepository.findAll().forEach(r -> log.info(r.toString()));
//
//        presentationRepository.save(new Presentation("PresetationOne"));
//        presentationRepository.save(new Presentation("PresetationTwo"));
//        presentationRepository.save(new Presentation("PresetationThree"));
//        presentationRepository.findAll().forEach(p -> log.info(p.toString()));
        User userA = new User("Ivan", "Petrow", "a", 1);
        Presentation presentationA = new Presentation("PresetationOne");
        Schedule schedule = new Schedule();
        schedule.setPresentation(presentationA);
        schedule.setUser(userA);
        schedule.setDate(LocalDate.now());

        Set<Schedule> schedules = new HashSet<>();
        schedules.add(schedule);
        userA.setSchedules(schedules);
//        userA.getSchedules().add(schedule);
//        presentationA.getSchedules().add(schedule);

        userRepository.save(userA);
        presentationRepository.save(presentationA);

        presentationRepository.findAll().forEach(p -> log.info(p.toString()));
        userRepository.findAll().forEach(u -> log.info(u.toString()));

        log.info(userA.toString());
        log.info(presentationA.toString());
    }
//todo user-schedule -> infinit object
//    https://stackoverflow.com/questions/35225053/tostring-function-for-jpa-one-to-many-relations-raises-stackoverflowerror
//    https://hellokoding.com/jpa-many-to-many-extra-columns-relationship-mapping-example-with-spring-boot-maven-and-mysql/

}
