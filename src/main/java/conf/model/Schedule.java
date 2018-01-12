package conf.model;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
//@Data
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column(name = "date")
    @Getter @Setter
    private LocalDate date;

    @Id
    @ManyToOne
    @JoinColumn(name = "presentation_id")
    @Getter @Setter
    private Presentation presentation;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

}
