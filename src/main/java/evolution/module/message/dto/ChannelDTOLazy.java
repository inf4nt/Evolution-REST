package evolution.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.user.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@JsonInclude
@EqualsAndHashCode(callSuper = true)
public class ChannelDTOLazy extends ChannelDTO {

    private List<UserDTO> channelUser;

    private List<MessageChannelDTO> messageChannelList;

    private UserDTO whoCreatedChannel;
}
