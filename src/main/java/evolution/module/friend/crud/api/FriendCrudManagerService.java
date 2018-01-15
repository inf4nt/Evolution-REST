package evolution.friend.crud.api;

import evolution.crud.PageableManager;
import evolution.friend.model.Friend;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FriendCrudManagerService extends PageableManager {

    Page<Friend> findRandomFriends(Long user, Integer size);

    Page<Friend> findAll(Integer page, Integer size);

    List<Friend> findAll();

    Optional<Friend> findOneFriend(Long user1, Long user2);

    Page<Friend> findFollowerByUser(Long userId, Integer page, Integer size);

    Page<Friend> findRequestByUser(Long userId, Integer page, Integer size);

    Page<Friend> findProgressByUser(Long userId, Integer page, Integer size);

    List<Friend> findFollowerByUser(Long userId);

    List<Friend> findRequestByUser(Long userId);

    List<Friend> findProgressByUser(Long userId);

    Page<Friend> findRequestFromUser(Long userId, Integer page, Integer size);

    List<Friend> findRequestFromUser(Long userId);

    Optional<Friend> sendRequestToFriend(Long senderId, Long recipientId);

    Optional<Friend> removeRequest(Long senderId, Long recipientId);

    Optional<Friend> removeFriend(Long senderId, Long recipientId);

    Optional<Friend> acceptRequest(Long senderId, Long recipientId);

    void clearRowByUserForeignKey(Long id);
}
