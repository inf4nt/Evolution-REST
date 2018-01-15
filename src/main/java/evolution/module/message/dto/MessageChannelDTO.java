package evolution.module.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.module.user.dto.UserDTO;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude
public class MessageChannelDTO {

    private Long id;

    private String text;

    private UserDTO sender;

    private ChannelDTO channel;

    private boolean isActive;

    private Date datePost;
}
