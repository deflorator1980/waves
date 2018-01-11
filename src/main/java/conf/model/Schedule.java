package conf.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
//@Table(name = "schedule")
public class Schedule implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Id
    @ManyToOne
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
