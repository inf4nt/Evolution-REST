package evolution.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by Infant on 06.11.2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {

    private Long id;

    private UserDTO sender;

    private UserDTO interlocutorId;

    private String text;

    private Long createdDateTimestamp;

    private String createdDateString;
}
