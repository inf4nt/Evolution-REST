package evolution.dto.model2;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickname;

    private String role;

    ////
    private Long idAdditionalData;

    private String username;

    private Date registrationDate;

    private String country;

    private String state;

    private GenderEnum gender;

    private boolean isBlock;

    private boolean isActive;

    private String secretKey;

    public UserDTO(Long id, String firstName, String lastName, String nickname, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.role = role;
    }

    public UserDTO(Long id, String firstName, String lastName, String nickname, String role, Long idAdditionalData, String username,
                   Date registrationDate, String country, String state, GenderEnum gender, boolean isBlock, boolean isActive, String secretKey) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.role = role;
        this.idAdditionalData = idAdditionalData;
        this.username = username;
        this.registrationDate = registrationDate;
        this.country = country;
        this.state = state;
        this.gender = gender;
        this.isBlock = isBlock;
        this.isActive = isActive;
        this.secretKey = secretKey;
    }
}
