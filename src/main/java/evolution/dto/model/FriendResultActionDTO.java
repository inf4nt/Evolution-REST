package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import lombok.Data;

@Data
@JsonInclude
public class FriendResultActionDTO {

    private UserDTO first;

    private UserDTO second;

    private UserDTO action;

    private FriendStatusEnum status;

    private FriendActionEnum nextAction;

}
