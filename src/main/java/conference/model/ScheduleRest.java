package conference.model;

import lombok.*;

import java.util.Date;

//@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRest extends PresentationRest{
    @Getter @Setter
    private String userName;
//    private String presentationName;
//    private Date presentationDate;
//    private String roomName;
}
