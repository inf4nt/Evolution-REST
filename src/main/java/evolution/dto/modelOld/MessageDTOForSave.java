package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 12.11.2017.
 */
@Data
@JsonInclude
public class MessageDTOForSave {

    private String text;

    private Long senderId;

    private Long recipientId;
}
