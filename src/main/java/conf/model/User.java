package conf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user")
//@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String passwd;
    @Getter @Setter
    private int role;

    @JsonManagedReference("user-schedule")
    @OneToMany(mappedBy = "user")
//    @JsonIgnore
    @Getter @Setter
    private Set<Schedule> schedules;

    public User(String firstName, String lastName, String passwd, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwd = passwd;
        this.role = role;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", passwd='" + passwd + '\'' +
//                ", role=" + role +
//                ", schedules=" + schedules +
//                '}';
//    }
}