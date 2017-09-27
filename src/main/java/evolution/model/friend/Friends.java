package evolution.model.friend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import evolution.common.FriendStatusEnum;
import evolution.model.user.UserLight;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Admin on 15.04.2017.
 */


@Entity
@Table(name = "friends")
@ToString @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Friends implements Serializable{

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_friends")
    @SequenceGenerator(name = "seq_friends", sequenceName = "seq_friends_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "bigint", nullable = false)
    private UserLight user;

    @ManyToOne
    @JoinColumn(name = "friend_id", columnDefinition = "bigint", nullable = false)
    private UserLight friend;

    @Column(name = "status")
    private Long status;

    public Friends(UserLight user, UserLight friend, Long status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public Friends(Long id, String firstName, String lastName, Long friendStatus) {
        this.user = new UserLight(id, firstName, lastName);
        this.status = friendStatus;
    }

    public Friends(Long id, String firstName, String lastName) {
        this.user = new UserLight(id, firstName, lastName);
    }

    public Friends(Long friendsId, String friendsFirstName) {
        this.user = new UserLight(friendsId, friendsFirstName);
    }

    public Friends(Long friendId, String friendFirstName, String friendLastName,
                   Long userId, String userFirstName, String userLastName) {
        this(userId, userFirstName, userLastName, null);
        this.friend = new UserLight(friendId, friendFirstName, friendLastName);
    }

    public Friends(Long friendId, String friendFirstName, String friendLastName,
                   Long userId, String userFirstName, String userLastName, Long status) {
        this(userId, userFirstName, userLastName, status);
        this.friend = new UserLight(friendId, friendFirstName, friendLastName);
    }

    @JsonProperty(value = "statusValue")
    public String getStatusValue() {
        return  Arrays
                .stream(FriendStatusEnum.values())
                .filter(o -> o.getId() == status)
                .findAny()
                .orElseThrow(NoSuchElementException::new).name();
    }

}


//    @Formula("(SELECT count(1) from friends f join user_data u on f.user_id = u.id WHERE f.user_id = friend_id and f.status = 1)")
//    private Long countFriends;
//
//    @Formula("(SELECT count(1) from friends f join user_data u on f.user_id = u.id WHERE f.user_id = friend_id and f.status = 2)")
//    private Long countFollowers;
//
//    @Formula("(SELECT count(1) from friends f join user_data u on f.user_id = u.id WHERE f.user_id = friend_id and f.status = 3)")
//    private Long countRequests;