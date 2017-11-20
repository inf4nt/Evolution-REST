package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.Data;

/**
 * Created by Infant on 11.11.2017.
 */
@Data
@JsonInclude
public class UserForUpdateDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickname;

    private UserAdditionalDataDTOForUpdate userAdditionalData = new UserAdditionalDataDTOForUpdate();

    @Data
    @JsonInclude
    public static class UserAdditionalDataDTOForUpdate {

        private String country;

        private String state;

        private GenderEnum gender;

    }
}
