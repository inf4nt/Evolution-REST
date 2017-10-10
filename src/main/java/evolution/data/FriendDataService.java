package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Infant on 09.10.2017.
 */
@Service
public class FriendDataService {

    private final FriendRepository friendRepository;

    private final SecuritySupportService securitySupportService;

    private static User first;

    private static User second;

    @Autowired
    public FriendDataService(FriendRepository friendRepository, SecuritySupportService securitySupportService) {
        this.friendRepository = friendRepository;
        this.securitySupportService = securitySupportService;
    }

    private void init(User user1, User user2) {
        if (user1.getId() > user2.getId()) {
            first = user1;
            second = user2;
        } else {
            first = user2;
            second = user1;
        }
    }

    private void init(Long senderRequest, Long someUser) {
        init(new User(senderRequest), new User(someUser));
    }

    private User getUserByIdFromFriendPk(Long userId, Friend friend) {
        if (friend.getPk().getFirst().getId().equals(userId))
            return friend.getPk().getFirst();
        else
            return friend.getPk().getSecond();
    }


    @Transactional(readOnly = true)
    public Optional<Friend> findOne(Long firstUserId, Long secondUserId) {
        return Optional.ofNullable(friendRepository.findOne(firstUserId, secondUserId));
    }

    @Transactional
    public Optional<Friend> requestFriend(User senderRequest, User someUser) {
        init(senderRequest, someUser);

        if (!isExist(first, second)) {
            Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, senderRequest);
            return Optional.of(friendRepository.save(friend));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean isExist(User first, User second) {
        return friendRepository.isExistByPk(first.getId(), second.getId()) != null;
    }

    @Transactional
    public Optional<Friend> removeFriend(Long otherUserId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User action = principal.get().getUser();

            init(action, new User(otherUserId));

            Optional<Friend> progress = findOne(first.getId(), second.getId());

            if (progress.isPresent() && FriendStatusEnum.PROGRESS == progress.get().getStatus()) {
                Friend friend = progress.get();
                friend.setStatus(FriendStatusEnum.REQUEST);

                friend.setActionUser(getUserByIdFromFriendPk(otherUserId, friend));
                return Optional.of(friendRepository.save(friend));
            }

        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> acceptRequest(Long otherUserId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User action = principal.get().getUser();

            init(action, new User(otherUserId));

            Optional<Friend> request = findOne(first.getId(), second.getId());

            if (request.isPresent()
                    && request.get().getStatus() == FriendStatusEnum.REQUEST
                    && !action.getId().equals(request.get().getActionUser().getId())) {

                Friend friend = request.get();
                friend.setStatus(FriendStatusEnum.PROGRESS);
                friend.setActionUser(action);
                return Optional.of(friendRepository.save(friend));
            }

        }

        return Optional.empty();
    }


}
