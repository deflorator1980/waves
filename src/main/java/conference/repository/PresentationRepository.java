package conference.repository;

import conference.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {
    Presentation findPresentationByName(String name);
}
//todo Optional<Presentation>