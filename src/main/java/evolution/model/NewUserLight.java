package evolution.model;

import evolution.common.UserRoleEnum;

import javax.persistence.*;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewUserLight {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user_data_id", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
