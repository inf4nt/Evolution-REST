package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 06.11.2017.
 */
@Data
@JsonInclude
public class MessageDTO {

    private Long id;

    private UserDTO sender;

    private DialogDTO dialogDTO;

    private String text;

    private Long createdDateTimestamp;

    private String createdDateString;
}
