package evolution.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import evolution.common.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
//@Entity
//@Table(name = "user_data")
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUser {

    // default data
    // default data
    // default data
    // default data

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user_data_id", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", columnDefinition = "varchar(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(255)")
    private String lastName;

    @Column(name = "nickname", columnDefinition = "varchar(255)")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRoleEnum role;

    // default data
    // default data
    // default data
    // default data

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private NewUserAdditionalData additionalData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUser newUser = (NewUser) o;

        if (id != null ? !id.equals(newUser.id) : newUser.id != null) return false;
        if (firstName != null ? !firstName.equals(newUser.firstName) : newUser.firstName != null) return false;
        if (lastName != null ? !lastName.equals(newUser.lastName) : newUser.lastName != null) return false;
        if (nickname != null ? !nickname.equals(newUser.nickname) : newUser.nickname != null) return false;
        if (role != newUser.role) return false;
        return additionalData != null ? additionalData.equals(newUser.additionalData) : newUser.additionalData == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (additionalData != null ? additionalData.hashCode() : 0);
        return result;
    }
}
