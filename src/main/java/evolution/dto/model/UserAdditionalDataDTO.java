package evolution.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * Created by Infant on 08.11.2017.
 */
@Data
@JsonInclude
public class UserAdditionalDataDTO {

    private Long id;

    private String username;

    private String password;

    private Date registrationDate;

    private String country;

    private String state;

    private GenderEnum gender;

    private boolean isBlock;

    private boolean isActive;

    private String secretKey;
}
