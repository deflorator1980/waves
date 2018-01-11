package conf;

import conf.model.Presentation;
import conf.model.Role;
import conf.model.Room;
import conf.model.User;
import conf.repos.PresentationRepository;
import conf.repos.RoleRepository;
import conf.repos.RoomRepository;
import conf.repos.UserRepository;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import javax.transaction.Transactional;

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
    }


}
