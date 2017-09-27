package evolution.model.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import evolution.common.UserRoleEnum;
import evolution.security.model.CustomSecurityUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Admin on 24.06.2017.
 */
@Entity
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLight {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(name = "role_id")
    private Long roleId;

    @Transient
    private String role;

    public UserLight(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roleId = user.getRoleId();
        this.role = getRole();
    }

    public UserLight(CustomSecurityUser customSecurityUser) {
        this(customSecurityUser.getUser());
    }

    public UserLight(Long id) {
        this.id = id;
    }

    public UserLight(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public UserLight(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRole() {
        if (roleId == null)
            return UserRoleEnum.USER.name();

        return Arrays.stream(UserRoleEnum.values())
                .filter(r -> r.getId() == this.roleId).map(Enum::name).findAny().orElse(UserRoleEnum.USER.name());
    }
}
