package conference.repository;

import conference.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByName(String name);
}
