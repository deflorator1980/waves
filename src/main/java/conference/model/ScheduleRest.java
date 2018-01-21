package conference.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRest extends PresentationRest{
    @Getter @Setter
    private String userName;
}
