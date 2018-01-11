package conf.repos;

import conf.model.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findByRoomId(int roomId);
}
