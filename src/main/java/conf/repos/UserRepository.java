package conf.repos;

import java.util.List;

import conf.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);
}