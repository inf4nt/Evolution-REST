package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.Data;

/**
 * Created by Infant on 09.11.2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTOForSave {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickname;

    private String username;

    private String country;

    private String state;

    private GenderEnum gender;
}
