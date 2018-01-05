package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
