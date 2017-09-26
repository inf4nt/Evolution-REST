package evolution.data;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.dao.FriendRepository;
import evolution.dao.FriendsDao;
import evolution.model.friend.Friends;
import evolution.model.user.UserLight;
import evolution.model.user.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.08.2017.
 */
@Service
public class FriendsDataService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final FriendRepository friendRepository;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public FriendsDataService(FriendRepository friendRepository, SecuritySupportService securitySupportService) {
        this.friendRepository = friendRepository;
        this.securitySupportService = securitySupportService;
    }

    @Transactional(readOnly = true)
    public List<Friends> findAll() {
        return friendRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional findOne(Long id) {
        return Optional.ofNullable(friendRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public List<Friends> findFriendsByStatusAndUser(Long userId, FriendStatusEnum status) {
        if (securitySupportService.isAllowed(userId)) {
            return friendRepository.findFriendsByStatusAndUser(userId, status.getId());
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean checkFriends(Long friendId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            Optional friend = Optional.ofNullable(friendRepository.checkFriends(principal.get().getUser().getId(), friendId, FriendStatusEnum.PROGRESS.getId()));
            if (friend.isPresent()) {
                LOGGER.info("friend exist");
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean actionFriends(Long friendsId, int action) {
        if (FriendActionEnum.ACCEPT_REQUEST.getId() == action) {

            return acceptFriends(friendsId);

        } else if (FriendActionEnum.DELETE_FRIEND.getId() == action) {

            return false;

        } else if (FriendActionEnum.DELETE_REQUEST.getId() == action) {

            return false;

        } else if (FriendActionEnum.REQUEST_FRIEND.getId() == action) {
            return requestFriend(friendsId);
        }

        return false;
    }

    /**
     * заявка на дружбу
     */
    @Transactional
    public boolean requestFriend(Long friendId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User user = principal.get().getUser();

            if (!checkFriends(friendId)) {
                Friends f1 = new Friends();
                f1.setUser(new UserLight(user)); // auth user
                f1.setFriend(new UserLight(friendId));
                f1.setStatus(FriendStatusEnum.REQUEST.getId());

                Friends f2 = new Friends();
                f2.setUser(new UserLight(friendId));
                f2.setFriend(new UserLight(user)); // auth user
                f2.setStatus(FriendStatusEnum.FOLLOWER.getId());

                friendRepository.save(f1);
                friendRepository.save(f2);

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param friendId с кем подтверждаю дружбу
     * @return
     */
    @Transactional
    public boolean acceptFriends(Long friendId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (!principal.isPresent())
            return false;

        User user = principal.get().getUser();

        Optional<Friends> auth = getFriendsByUserIdAndStatus(user.getId(), friendId, FriendStatusEnum.FOLLOWER.getId());
        if (!auth.isPresent() || !auth.get().getStatus().equals(FriendStatusEnum.FOLLOWER.getId()))
            return false;

        Optional<Friends> other = getFriendsByUserIdAndStatus(friendId, user.getId(), FriendStatusEnum.REQUEST.getId());
        if (!other.isPresent() || !other.get().getStatus().equals(FriendStatusEnum.REQUEST.getId()))
            return false;

        Friends f1 = auth.get();
        Friends f2 = auth.get();

        f1.setStatus(FriendStatusEnum.PROGRESS.getId());
        f2.setStatus(FriendStatusEnum.PROGRESS.getId());

        friendRepository.save(f1);
        friendRepository.save(f2);

        return true;
    }

    @Transactional(readOnly = true)
    public Optional<Friends> getFriendsByUserIdAndStatus(Long authUserId, Long friendId, Long status) {
        return Optional.ofNullable(friendRepository.getFriendsByUserIdAndStatus(authUserId, friendId, status));
    }

    public void friendRequest(long authUserId, long id2) {
        UserLight authUser = new UserLight(authUserId);
        UserLight user2 = new UserLight(id2);
        Friends request = new Friends(authUser, user2, FriendStatusEnum.REQUEST.getId());
        Friends follower = new Friends(user2, authUser, FriendStatusEnum.FOLLOWER.getId());
        friendRepository.save(follower);
        friendRepository.save(request);
    }
}
