package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 06.11.2017.
 */
@Data
@JsonInclude
@Deprecated
public class MessageDTO {

    private Long id;

    private String content;

    private UserDTO sender;

    private DialogDTO dialog;

    private String text;

    private Long createdDateTimestamp;

    private String createdDateString;
}
