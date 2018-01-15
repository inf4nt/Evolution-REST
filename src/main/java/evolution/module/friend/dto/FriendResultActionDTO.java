package evolution.module.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.friend.common.FriendActionEnum;
import evolution.friend.common.RelationshipStatus;
import evolution.module.user.dto.UserDTO;
import lombok.Data;

@Data
@JsonInclude
public class FriendResultActionDTO {

    private UserDTO first;

    private UserDTO second;

    private UserDTO actionUser;

    private RelationshipStatus status;

    private FriendActionEnum nextAction;

}
