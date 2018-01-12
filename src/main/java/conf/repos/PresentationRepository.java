package conf.repos;

import conf.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//public interface PresentationRepository extends CrudRepository<Presentation, Long > {
public interface PresentationRepository extends JpaRepository<Presentation, Long > {
}
