package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendStatusEnum;
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

    private FriendStatusEnum status;
}
