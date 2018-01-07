package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class MessageChannelSaveDTO {

    private String text;

    private Long channelId;

    private Long senderId;
}
