package conference.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "presentation")
    private Set<Schedule> schedules;

    public Presentation(String name){
        this.name = name;
    }

}