package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 12.11.2017.
 */
@Data
@JsonInclude
public class MessageSaveDTO {

    private String text;

    private Long senderId;

    private Long recipientId;
}
