package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 08.11.2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFullDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickname;

    private String role;

    private UserAdditionalDataDTO userAdditionalData;
}
