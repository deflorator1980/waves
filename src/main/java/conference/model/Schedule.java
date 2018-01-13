package conference.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedule")
//@Data
public class Schedule implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "presentation_id")
    @Getter @Setter
    private Presentation presentation;

//    @Column(name = "date")
    @Getter @Setter
    private Date date;

    @Getter @Setter
    private int room;

}