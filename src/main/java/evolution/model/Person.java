package evolution.model;

import evolution.common.UserRoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 11.11.2017.
 */
@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person")
    @SequenceGenerator(name = "seq_person", sequenceName = "seq_person_id", allocationSize = 1)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, columnDefinition = "varchar(255)")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "first_name", columnDefinition = "varchar(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(255)")
    private String lastName;

    @Column(name = "nickname", columnDefinition = "varchar(255)")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRoleEnum role;

    @Column(name = "registration_date", columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(columnDefinition = "varchar(255)")
    private String country;

    @Column(columnDefinition = "varchar(255)")
    private String state;

    @Column(name = "is_block", columnDefinition = "boolean default false")
    private boolean isBlock;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "secret_key", columnDefinition = "varchar(255)", unique = true, nullable = false)
    private String secretKey;

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;

    public Person(Long id) {
        this.id = id;
    }
}
