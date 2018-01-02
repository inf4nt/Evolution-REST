package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 12.11.2017.
 */
@Data
@JsonInclude
public class MessageDTOFull {

    private Long id;

    private String text;

    private UserDTO sender;

    private DialogDTO dialogDTO;

    private Long createdDateTimestamp;

    private String createdDateString;
}
