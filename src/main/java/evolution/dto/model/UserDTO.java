package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by Infant on 07.11.2017.
 */
@Data
@JsonInclude
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickname;
}
