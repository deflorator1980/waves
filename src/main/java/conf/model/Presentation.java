package conf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Data
@NoArgsConstructor
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
    @Getter @Setter
    private Set<Schedule> schedules;


    public Presentation(String name) {
        this.name = name;
    }

}
