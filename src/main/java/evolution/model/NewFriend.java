package evolution.model;

import evolution.common.FriendStatusEnum;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewFriend {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_friends")
    @SequenceGenerator(name = "seq_friends", sequenceName = "seq_friends_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "bigint", nullable = false)
    private NewUserLight user;

    @ManyToOne
    @JoinColumn(name = "friend_id", columnDefinition = "bigint", nullable = false)
    private NewUserLight friend;

    @Enumerated(EnumType.STRING)
    private FriendStatusEnum status;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "is_active")
    private boolean isActive;
}
