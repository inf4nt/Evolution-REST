package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.*;

import java.util.Date;



@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOLazy extends UserDTO {

    private String country;

    private String state;

    private GenderEnum gender;

    private String username;

    private Date registrationDate;

    private boolean isBlock;

    private boolean isActive;

    private String secretKey;
}
