package evolution.module.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.friend.common.FriendActionEnum;
import lombok.Data;

@Data
@JsonInclude
public class FriendActionDTO {

    private Long actionUserId;

    private Long recipientUserId;

    private FriendActionEnum action;
}
