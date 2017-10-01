package evolution.data;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.common.ServiceStatus;
import evolution.model.friend.Friends;
import evolution.model.user.UserLight;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public FriendsDataService(FriendRepository friendRepository, SecuritySupportService securitySupportService) {
        this.friendRepository = friendRepository;
        this.securitySupportService = securitySupportService;
    }

    public ServiceStatus actionFriends(Long friendsId, FriendActionEnum action) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();

        if (!principal.isPresent()) {
            LOGGER.warn("principal is null");
            return ServiceStatus.EXPECTATION_FAILED;
        }

        return actionFriendsAdminService(principal.get().getUser().getId(), friendsId, action);
    }

    public ServiceStatus actionFriendsAdminService(Long user1, Long user2, FriendActionEnum action) {
        if (FriendActionEnum.ACCEPT_REQUEST == action) {

            return acceptFriendsAdminService(user1, user2);

        } else if (FriendActionEnum.DELETE_FRIEND == action) {

            return deleteFriendAdminService(user1, user2);

        } else if (FriendActionEnum.DELETE_REQUEST == action) {

            return deleteRequestAdminService(user1, user2);

        } else if (FriendActionEnum.REQUEST_FRIEND == action) {

            return requestFriendAdminService(user1, user2);

        }
        LOGGER.warn("method not found");
        return ServiceStatus.NOT_STARTED;
    }

    @Transactional(readOnly = true)
    public String findFriendStatusByUsers(Long user1, Long user2) {

        Long status = friendRepository.findFriendStatusByUsers(user1, user2);
        return  Arrays
                .stream(FriendStatusEnum.values())
                .filter(o -> o.getId().equals(status))
                .findAny()
                .orElse(FriendStatusEnum.NOT_FOUND).name().toLowerCase();
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
        if (securitySupportService.isAllowed(userId) == ServiceStatus.TRUE) {
            return friendRepository.findFriendsByStatusAndUser(userId, status.getId());
        } else {
            LOGGER.warn("fail");
            return new ArrayList<>();
        }
    }

    @Transactional
    public ServiceStatus deleteRequestAdminService(Long senderRequest, Long user2) {
        Optional<Friends> sender = getFriendsByUserIdAndStatus(senderRequest, user2, FriendStatusEnum.REQUEST.getId());

        if (!sender.isPresent()) {
            return ServiceStatus.EXPECTATION_FAILED;
        }

        if (!FriendStatusEnum.REQUEST.getId().equals(sender.get().getStatus())) {
            return ServiceStatus.FALSE;
        }

        Optional<Friends> other = getFriendsByUserIdAndStatus(user2, senderRequest, FriendStatusEnum.FOLLOWER.getId());

        if (!other.isPresent()) {
            return ServiceStatus.EXPECTATION_FAILED;
        }

        if (!FriendStatusEnum.FOLLOWER.getId().equals(other.get().getStatus())) {
            return ServiceStatus.FALSE;
        }

        friendRepository.delete(sender.get());
        friendRepository.delete(other.get());

        return ServiceStatus.TRUE;
    }

    @Transactional
    public ServiceStatus deleteFriendAdminService(Long senderRequest, Long user2) {
        Optional<Friends> sender = getFriendsByUserIdAndStatus(senderRequest, user2, FriendStatusEnum.PROGRESS.getId());

        if (!sender.isPresent()) {
            return ServiceStatus.EXPECTATION_FAILED;
        }

        if (!FriendStatusEnum.PROGRESS.getId().equals(sender.get().getStatus())) {
            return ServiceStatus.FALSE;
        }

        Optional<Friends> removed = getFriendsByUserIdAndStatus(user2, senderRequest, FriendStatusEnum.PROGRESS.getId());

        if (!removed.isPresent()) {
            return ServiceStatus.EXPECTATION_FAILED;
        }

        if (!FriendStatusEnum.PROGRESS.getId().equals(removed.get().getStatus())) {
            return ServiceStatus.FALSE;
        }

        Friends f1 = sender.get();
        Friends f2 = removed.get();

        f1.setStatus(FriendStatusEnum.FOLLOWER.getId());
        f2.setStatus(FriendStatusEnum.REQUEST.getId());

        friendRepository.save(f1);
        friendRepository.save(f2);

        return ServiceStatus.TRUE;
    }

    @Transactional
    public ServiceStatus requestFriendAdminService(Long senderRequestUserId, Long user2) {
        if (existFriend(senderRequestUserId, user2) == ServiceStatus.FALSE) {

            Friends f1 = new Friends();
            f1.setUser(new UserLight(senderRequestUserId)); // auth user
            f1.setFriend(new UserLight(user2));
            f1.setStatus(FriendStatusEnum.REQUEST.getId());

            Friends f2 = new Friends();
            f2.setUser(new UserLight(user2));
            f2.setFriend(new UserLight(senderRequestUserId)); // auth user
            f2.setStatus(FriendStatusEnum.FOLLOWER.getId());

            friendRepository.save(f1);
            friendRepository.save(f2);

            return ServiceStatus.TRUE;
        } else {
            LOGGER.warn("request to friends, failed");
            return ServiceStatus.FALSE;
        }

    }

    /**
     * @param confirmingUserId
     * @return
     */
    @Transactional
    public ServiceStatus acceptFriendsAdminService(Long confirmingUserId, Long senderRequestUserId) {

        Optional<Friends> auth = getFriendsByUserIdAndStatus(confirmingUserId, senderRequestUserId, FriendStatusEnum.FOLLOWER.getId());

        if (!auth.isPresent() || !auth.get().getStatus().equals(FriendStatusEnum.FOLLOWER.getId()))
            return ServiceStatus.EXPECTATION_FAILED;

        Optional<Friends> other = getFriendsByUserIdAndStatus(senderRequestUserId, confirmingUserId, FriendStatusEnum.REQUEST.getId());
        if (!other.isPresent() || !other.get().getStatus().equals(FriendStatusEnum.REQUEST.getId()))
            return ServiceStatus.EXPECTATION_FAILED;

        Friends f1 = auth.get();
        Friends f2 = other.get();

        f1.setStatus(FriendStatusEnum.PROGRESS.getId());
        f2.setStatus(FriendStatusEnum.PROGRESS.getId());

        friendRepository.save(f1);
        friendRepository.save(f2);

        return ServiceStatus.TRUE;
    }

    @Transactional(readOnly = true)
    public Optional<Friends> getFriendsByUserIdAndStatus(Long authUserId, Long friendId, Long status) {
        return Optional.ofNullable(friendRepository.getFriendsByUserIdAndStatus(authUserId, friendId, status));
    }

    @Transactional(readOnly = true)
    public ServiceStatus existFriend(Long user1, Long user2) {
        if (friendRepository.existFriend(user1, user2).size() == 2) {
            return ServiceStatus.TRUE;
        } else {
            return ServiceStatus.FALSE;
        }
    }
}
