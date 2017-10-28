package evolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.GenderEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "user_additional_data")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAdditionalData {

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

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column
    private String country;

    @Column
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "is_block", columnDefinition = "boolean default false")
    private boolean isBlock;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "secret_key", columnDefinition = "varchar(255)", unique = true)
    private String secretKey;

    //Additional data
    //Additional data
    //Additional data
    //Additional data

    @Version
    @Column(columnDefinition = "bigint")
    private Long version;
}
