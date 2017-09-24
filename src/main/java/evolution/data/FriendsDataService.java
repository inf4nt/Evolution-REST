package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.dao.FriendRepository;
import evolution.dao.FriendsDao;
import evolution.model.friend.Friends;
import evolution.model.user.UserLight;
import evolution.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Infant on 08.08.2017.
 */
@Service
public class FriendsDataService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendsDao friendsDao;

    public Long countFriends(Long userId) {
//        return friendRepository.countFriendsByStatus(userId, FriendStatusEnum.PROGRESS.getId());
        return null;
    }

    public Friends findUserAndFriendStatus(Long authUserId, Long id) {
//        return friendRepository.findUserAndFriendStatus(authUserId, id);
        return null;
    }

    public Long countFollowers(Long userId) {
//        return friendRepository.countFriendsByStatus(userId, FriendStatusEnum.FOLLOWER.getId());
        return null;
    }

    public Long countRequests(Long userId) {
//        return friendRepository.countFriendsByStatus(userId, FriendStatusEnum.REQUEST.getId());
        return null;
    }

    public List<User> randomFriends(Long userId, Pageable pageable) {
//        return friendRepository.randomFriends(userId, FriendStatusEnum.PROGRESS.getId(), pageable);
        return null;
    }

    public void friendRequest(long authUserId, long id2){
        UserLight authUser = new UserLight(authUserId);
        UserLight user2 = new UserLight(id2);
        Friends request = new Friends(authUser, user2, FriendStatusEnum.REQUEST.getId());
        Friends follower = new Friends(user2, authUser, FriendStatusEnum.FOLLOWER.getId());
        friendRepository.save(follower);
        friendRepository.save(request);
    }
}
