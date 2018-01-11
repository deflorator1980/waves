package conf.model;

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
    private Set<Schedule> schedules;


    public Presentation(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Schedule> getSchedules() {
        return schedules;
    }
}
