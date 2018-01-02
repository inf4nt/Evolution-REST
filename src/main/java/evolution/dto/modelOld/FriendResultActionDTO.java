package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendActionEnum;
import evolution.common.RelationshipStatus;
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
