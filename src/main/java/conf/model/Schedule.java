package conf.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "schedule")
//@Data
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule implements Serializable{

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Getter @Setter
//    private Long id;

    @Column(name = "date")
    @Getter @Setter
    private LocalDate date;

    @Id
    @JsonBackReference("schedule-presentation")
    @ManyToOne
    @JoinColumn(name = "presentation_id")
    @Getter @Setter
    private Presentation presentation;

    @Id
    @JsonBackReference("schedule-user")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Override
    public String toString() {
        return "Schedule{" +
                "date=" + getDate() +
                ", presentation=" + getPresentation() +
                ", user=" + getUser() +
                '}';
    }
}
