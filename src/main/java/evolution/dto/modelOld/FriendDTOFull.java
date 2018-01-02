package evolution.dto.modelOld;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.RelationshipStatus;
import lombok.Data;

@Data
@JsonInclude
public class FriendDTOFull {

    private FriendEmbeddableDTO pk;

    private RelationshipStatus status;

    private UserDTO actionUser;

    private FriendActionDTO nextAction;

    @Data
    @JsonInclude
    public static class FriendEmbeddableDTO {

        private UserDTO first;

        private UserDTO second;
    }

}
