package evolution.module.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.Date;

@Data
@JsonInclude
public class ChannelDTO {

    private Long id;

    private String channelName;

    private Date dateCreate;

    private boolean isActive;

    private boolean isPrivate;
}
