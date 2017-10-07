package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import evolution.model.user.User;
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
//@Table(name = "user_additional_data")
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUserAdditionalData {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_additional_data")
    @SequenceGenerator(name = "seq_user_additional_data", sequenceName = "seq_user_additional_data_id", allocationSize = 1)
    private Long id;

    //Additional data
    //Additional data
    //Additional data
    //Additional data

    @Column(name = "username", unique = true, nullable = false, columnDefinition = "varchar(255)")
    private String username;

    @Column(name = "password", unique = true, nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column
    private String country;

    @Column
    private String state;

    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;

    @Column(name = "is_active")
    private boolean isActive;

    //Additional data
    //Additional data
    //Additional data
    //Additional data

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "bigint")
    private NewUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUserAdditionalData that = (NewUserAdditionalData) o;

        if (isActive != that.isActive) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
            return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (genderEnum != that.genderEnum) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (genderEnum != null ? genderEnum.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
