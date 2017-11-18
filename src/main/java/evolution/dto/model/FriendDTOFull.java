package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendStatusEnum;
import lombok.Data;

@Data
@JsonInclude
public class FriendDTOFull {

    private FriendEmbeddableDTO pk;

    private FriendStatusEnum status;

    private UserDTO actionUser;

    @Data
    @JsonInclude
    public static class FriendEmbeddableDTO {

        private UserDTO first;

        private UserDTO second;
    }

}
