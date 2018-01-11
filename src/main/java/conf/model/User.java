package conf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String passwd;
    private int role;
    private Set<Schedule> schedules;

    public User(String firstName, String lastName, String passwd, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwd = passwd;
        this.role = role;
    }

    @OneToMany(mappedBy = "user")
    public Set<Schedule> getSchedules() {
        return schedules;
    }
}