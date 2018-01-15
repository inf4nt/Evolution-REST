package evolution.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.friend.common.RelationshipStatus;
import evolution.user.dto.UserDTO;
import lombok.Data;

@Data
@JsonInclude
public class FriendDTOLazy {

    private UserDTO first;

    private UserDTO second;

    private RelationshipStatus status;

    private UserDTO actionUser;

    private FriendActionDTO nextAction;
}
