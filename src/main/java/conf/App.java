package conf;

import conf.model.User;
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

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Transactional
    @Override
    public void run(String... strings) throws Exception {

        userRepository.save(new User("Ivan", "Petrow", "a", 1));
        userRepository.save(new User("Petr", "Ivanow", "a", 1));
        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }
    }

//    @Bean
//    public CommandLineRunner demo(CustomerRepository repository) {
//        return (args) -> {
//            // save a couple of customers
//            repository.save(new Customer("Jack", "Bauer"));
//            repository.save(new Customer("Chloe", "O'Brian"));
//            repository.save(new Customer("Kim", "Bauer"));
//            repository.save(new Customer("David", "Palmer"));
//            repository.save(new Customer("Michelle", "Dessler"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (Customer customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            Customer customer = repository.findOne(1L);
//            log.info("Customer found with findOne(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            for (Customer bauer : repository.findByLastName("Bauer")) {
//                log.info(bauer.toString());
//            }
//            log.info("");
//        };
//    }
}
