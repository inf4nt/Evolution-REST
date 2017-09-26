package evolution.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oracle.webservices.internal.api.message.PropertySet;
import evolution.common.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Created by Admin on 09.03.2017.
 */
@Entity
@Table(name = "user_data")
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user_data_id", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column
    private String country;

    @Column
    private String state;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //todo: in future delete this

    @JsonIgnore
    public void updateFields(User user) {
//        this.id = user.id;
//        this.login = user.login;
//        this.password = user.password;
//        this.firstName = user.firstName;
//        this.lastName = user.lastName;
//        this.roleId = user.roleId;
//        this.registrationDate = user.registrationDate;
    }

    @JsonProperty(value = "roleValue")
    public String getRole() {
        return Arrays.stream(UserRoleEnum.values())
                .filter(r -> r.getId() == this.roleId)
                .findAny()
                .orElseThrow(NoSuchElementException::new).name();
    }

    @JsonIgnore
    public String getDateFormatRegistrationDate() {
        return DateFormat.getInstance().format(registrationDate);
    }
}
