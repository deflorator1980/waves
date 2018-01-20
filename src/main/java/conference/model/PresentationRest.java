package conference.model;

import lombok.Data;

import java.util.Date;

@Data
public class PresentationRest {
    private String presentationName;
    private Date presentationDate;
    private String roomName;
}
