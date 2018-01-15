package evolution.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class UserSetPasswordDTO {

    private Long id;

    private String oldPassword;

    private String newPassword;
}
