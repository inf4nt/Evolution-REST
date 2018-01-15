package evolution.module.user.model;


import evolution.user.common.UserRoleEnum;
import evolution.security.dto.CustomSecurityUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
public class User implements Serializable {

    // default data
    // default data
    // default data
    // default data

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user_data_id", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", columnDefinition = "varchar(255)", nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(255)", nullable = false)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "user_additional_data_id", columnDefinition = "bigint", nullable = false)
    private UserAdditionalData userAdditionalData = new UserAdditionalData();

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstName, String lastName, String nickname, UserRoleEnum role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.role = role;
    }

    public User(CustomSecurityUser customSecurityUser) {
        this.id = customSecurityUser.getUser().getId();
        this.firstName = customSecurityUser.getUser().getFirstName();
        this.lastName = customSecurityUser.getUser().getLastName();
        this.nickname = customSecurityUser.getUser().getNickname();
        this.role = customSecurityUser.getUser().getRole();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", version=" + version +
                '}';
    }

    public boolean equalsById(Long id) {
        return this.id.equals(id);
    }
}
