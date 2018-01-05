package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.model.User;
import evolution.model.channel.MessageChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude
@EqualsAndHashCode(callSuper = true)
public class ChannelDTOLazy extends ChannelDTO {

    private List<UserDTO> channelUser;

    private List<MessageChannelDTO> messageChannelList;

    private UserDTO whoCreatedChannel;
}
