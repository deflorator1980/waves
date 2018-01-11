package conf;

import conf.model.*;
import conf.repos.*;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

    @Autowired
    ScheduleRepository scheduleRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Transactional
    @Override
    public void run(String... strings) throws Exception {

        userRepository.save(new User("Ivan", "Petrow", "a", 1));
        userRepository.save(new User("Petr", "Ivanow", "a", 1));
        userRepository.findAll().forEach(u -> log.info(u.toString()));

        roomRepository.save(new Room("one"));
        roomRepository.save(new Room("two"));
        roomRepository.findAll().forEach(r -> log.info(r.toString()));

        roleRepository.save(new Role("Admin"));
        roleRepository.save(new Role("Presenter"));
        roleRepository.save(new Role("Listener"));
        roleRepository.findAll().forEach(r -> log.info(r.toString()));

        presentationRepository.save(new Presentation("PresetationOne"));
        presentationRepository.save(new Presentation("PresetationTwo"));
        presentationRepository.save(new Presentation("PresetationThree"));
        presentationRepository.findAll().forEach(p -> log.info(p.toString()));

        scheduleRepository.save(new Schedule(LocalDate.of(2018, 2, 16), 1, 1));
        scheduleRepository.save(new Schedule(LocalDate.of(2018, 1, 16), 1, 1));
        scheduleRepository.save(new Schedule(LocalDate.of(2018, 4, 24), 2, 2));
        scheduleRepository.save(new Schedule(LocalDate.of(2018, 4, 25), 2, 2));
        scheduleRepository.findAll().forEach(s -> log.info(s.toString()));
        scheduleRepository.findByRoomId(1).forEach(s -> log.info("room one: " + s.toString()));

        for (Room room : roomRepository.findAll()) {
            for (Schedule schedule : scheduleRepository.findByRoomId(room.getId())){
                log.info(schedule.toString());
            }
        }

        roomRepository.findAll().forEach(room -> {
            scheduleRepository.findByRoomId(room.getId()).forEach(schedule -> log.info(schedule.toString()));
        });
    }
//todo schedule.setUser etc
//    https://hellokoding.com/jpa-many-to-many-extra-columns-relationship-mapping-example-with-spring-boot-maven-and-mysql/

}
