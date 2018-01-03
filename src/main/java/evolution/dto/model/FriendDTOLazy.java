package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.RelationshipStatus;
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
