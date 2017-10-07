package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import evolution.common.FriendStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
//@Entity
//@Table(name = "friends")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewFriend {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_friends")
    @SequenceGenerator(name = "seq_friends", sequenceName = "seq_friends_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "bigint", nullable = false)
    private NewUser user;

    @ManyToOne
    @JoinColumn(name = "friend_id", columnDefinition = "bigint", nullable = false)
    private NewUser friend;

    @Enumerated(EnumType.STRING)
    private FriendStatusEnum status;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "is_active")
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewFriend newFriend = (NewFriend) o;

        if (isActive != newFriend.isActive) return false;
        if (id != null ? !id.equals(newFriend.id) : newFriend.id != null) return false;
        if (user != null ? !user.equals(newFriend.user) : newFriend.user != null) return false;
        if (friend != null ? !friend.equals(newFriend.friend) : newFriend.friend != null) return false;
        if (status != newFriend.status) return false;
        return date != null ? date.equals(newFriend.date) : newFriend.date == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (friend != null ? friend.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
