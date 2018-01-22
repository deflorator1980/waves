package conference.repository;


import conference.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findRoomByName(String name);
 }
