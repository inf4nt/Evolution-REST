package evolution.model;

import evolution.model.user.User;

import javax.persistence.*;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewUserAdditionalData {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_additional_data")
    @SequenceGenerator(name = "seq_user_additional_data", sequenceName = "seq_user_additional_data_id", allocationSize = 1)
    private Long id;

    //Additional data

    @OneToOne
    @JoinColumn(name = "user_id", columnDefinition = "bigint")
    private NewUser user;
}
