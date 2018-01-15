package evolution.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.friend.common.RelationshipStatus;
import evolution.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Infant on 08.11.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class FriendDTO {

    private UserDTO first;

    private UserDTO second;

    private UserDTO action;

    private RelationshipStatus status;
}
