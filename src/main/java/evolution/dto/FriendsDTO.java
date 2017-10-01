package evolution.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendStatusEnum;
import lombok.Data;

import java.util.Arrays;

/**
 * Created by Infant on 01.10.2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendsDTO {

    private Long userId;
    private Long friendId;
    private String status;

    public FriendsDTO(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public void setStatus(Long status) {
        this.status = Arrays
                .stream(FriendStatusEnum.values())
                .filter(o -> o.getId().equals(status))
                .findAny()
                .orElse(FriendStatusEnum.NOT_FOUND).name();
    }
}
