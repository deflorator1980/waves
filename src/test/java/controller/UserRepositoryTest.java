//package controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import conference.App;
//import conference.model.User;
//import conference.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = App.class)
//@DataJpaTest
//public class UserRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testFindByUserName() {
//        User user = new User("UserTest");
//        user.setRole(1);
//        entityManager.persist(user);
//
//        User findByUserName = userRepository.findUserByName(user.getName());
//        List<User> list = new ArrayList<>();
//        list.add(findByUserName);
//
//        assertThat(list).extracting(User::getRole).containsOnly(user.getRole());
//    }
//}
