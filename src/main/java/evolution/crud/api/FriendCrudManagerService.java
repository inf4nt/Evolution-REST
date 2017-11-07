package evolution.crud.api;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FriendCrudManagerService extends PageableManager {

    Page<Friend> findAll(Integer page, Integer size);

    List<Friend> findAll();

    Optional<Friend> findOneFriend(Long user1, Long user2);

    Page<Friend> findFollowerByUser(Long userId, FriendStatusEnum followerStatus, Integer page, Integer size);

    Page<Friend> findRequestByUser(Long userId, FriendStatusEnum requestStatus, Integer page, Integer size);

    Page<Friend> findProgressByUser(Long userId, FriendStatusEnum progressStatus, Integer page, Integer size);

    List<Friend> findFollowerByUser(Long userId, FriendStatusEnum followerStatus);

    List<Friend> findRequestByUser(Long userId, FriendStatusEnum requestStatus);

    List<Friend> findProgressByUser(Long userId, FriendStatusEnum progressStatus);

    Optional<Friend> sendRequestToFriend(Long senderId, Long recipientId);

    Optional<Friend> removeRequest(Long senderId, Long recipientId);

    Optional<Friend> removeFriend(Long senderId, Long recipientId);

    Optional<Friend> acceptRequest(Long senderId, Long recipientId);

}
