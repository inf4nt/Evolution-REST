package evolution.data;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import evolution.model.User;
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
 * Created by Infant on 09.10.2017.
 */
@Service
public class FriendDataService {

    private final FriendRepository friendRepository;

    private final SecuritySupportService securitySupportService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

    public List<Friend> findByStatus(FriendStatusEnum status, Long userId) {
        switch (status) {
            case FOLLOWER:
                return findFollowerByUserId(userId);
            case REQUEST:
                return findRequestByUserId(userId);
            case PROGRESS:
                return findProgressByUserId(userId);
        }
        LOGGER.info("status not found");
        return new ArrayList<>();
    }

    public Optional<Friend> actionFriend(FriendActionEnum action, Long otherUserId) {
        switch (action) {
            case DELETE_FRIEND:
                return removeFriend(otherUserId);
            case ACCEPT_REQUEST:
                return acceptRequest(otherUserId);
            case DELETE_REQUEST:
                return removeRequest(otherUserId);
            case REQUEST_FRIEND:
                return requestFriend(otherUserId);
        }
        LOGGER.info("action not found");
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Friend> findFollowerByUserId(Long userId) {
        return friendRepository.findFollowerByUser(userId, FriendStatusEnum.FOLLOWER);
    }

    @Transactional(readOnly = true)
    public List<Friend> findRequestByUserId(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST);
    }

    @Transactional(readOnly = true)
    public List<Friend> findProgressByUserId(Long userId) {
        return friendRepository.findProgressByUser(userId, FriendStatusEnum.PROGRESS);
    }

    @Transactional(readOnly = true)
    public Optional<Friend> findOne(Long firstUserId, Long secondUserId) {
        return Optional.ofNullable(friendRepository.findOne(firstUserId, secondUserId));
    }

    @Transactional
    public Optional<Friend> requestFriend(Long otherUserId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User action = principal.get().getUser();

            init(action.getId(), otherUserId);

            Optional<Friend> exist = findOne(first.getId(), second.getId());

            // todo create DTO
            if (!exist.isPresent()) {
                Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, action);
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("request friend failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + exist.get());
                return exist;
            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> removeFriend(Long otherUserId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User action = principal.get().getUser();

            init(action, new User(otherUserId));

            Optional<Friend> progress = findOne(first.getId(), second.getId());

            if (progress.isPresent()) {

                if (FriendStatusEnum.PROGRESS == progress.get().getStatus()) {
                    Friend friend = progress.get();
                    friend.setStatus(FriendStatusEnum.REQUEST);
                    friend.setActionUser(getUserByIdFromFriendPk(otherUserId, friend));
                    return Optional.of(friendRepository.save(friend));
                } else {
                    LOGGER.info("remove friend failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + progress.get());
                    return progress;
                }

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
            if (request.isPresent()) {

                if (request.get().getStatus() == FriendStatusEnum.REQUEST && !action.getId().equals(request.get().getActionUser().getId())) {
                    Friend friend = request.get();
                    friend.setStatus(FriendStatusEnum.PROGRESS);
                    friend.setActionUser(action);
                    return Optional.of(friendRepository.save(friend));
                } else {
                    LOGGER.info("accept request failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                    return request;
                }

            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> removeRequest(Long otherUserId) {
        Optional<CustomSecurityUser> principal = securitySupportService.getPrincipal();
        if (principal.isPresent()) {
            User action = principal.get().getUser();

            init(action.getId(), otherUserId);

            Optional<Friend> request = findOne(first.getId(), second.getId());
            if (request.isPresent()) {

                if (FriendStatusEnum.REQUEST == request.get().getStatus()
                        && request.get().getActionUser().getId().equals(action.getId())) {

                    friendRepository.delete(request.get());
                    LOGGER.info("remove request successful");
                } else {
                    LOGGER.info("remove request failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                    return request;
                }

            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> requestFriendAdmin(Long actionUserId, Long otherUserId) {
        User action = new User(actionUserId);

        init(actionUserId, otherUserId);

        Optional<Friend> exist = findOne(first.getId(), second.getId());

        // todo create DTO
        if (!exist.isPresent()) {
            Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, action);
            return Optional.of(friendRepository.save(friend));
        } else {
            LOGGER.info("request friend failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + exist.get());
            return exist;
        }
    }

    @Transactional
    public Optional<Friend> removeRequestAdmin(Long actionUserId, Long otherUserId) {

        User action = new User(actionUserId);

        init(action.getId(), otherUserId);

        Optional<Friend> request = findOne(first.getId(), second.getId());
        if (request.isPresent()) {

            if (FriendStatusEnum.REQUEST == request.get().getStatus()
                    && request.get().getActionUser().getId().equals(action.getId())) {

                friendRepository.delete(request.get());
                LOGGER.info("remove request successful");
            } else {
                LOGGER.info("remove request failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> acceptRequestAdmin(Long actionUserId, Long otherUserId) {

        User action = new User(actionUserId);

        init(action, new User(otherUserId));

        Optional<Friend> request = findOne(first.getId(), second.getId());
        if (request.isPresent()) {

            if (request.get().getStatus() == FriendStatusEnum.REQUEST && !action.getId().equals(request.get().getActionUser().getId())) {
                Friend friend = request.get();
                friend.setStatus(FriendStatusEnum.PROGRESS);
                friend.setActionUser(action);
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("accept request failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }


}
