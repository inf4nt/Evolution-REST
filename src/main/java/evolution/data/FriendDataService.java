package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Friend> findAll(Sort sort) {
        return friendRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Page<Friend> findAll(Pageable pageable) {
        return friendRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Friend> findUserFollowers(Long userId) {
        return friendRepository.findFollowerByUser(userId, FriendStatusEnum.FOLLOWER);
    }

    @Transactional(readOnly = true)
    public List<Friend> findUserRequests(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST);
    }

    @Transactional(readOnly = true)
    public List<Friend> findUserProgress(Long userId) {
        return friendRepository.findProgressByUser(userId, FriendStatusEnum.PROGRESS);
    }


    @Transactional(readOnly = true)
    public Page<Friend> findUserFollowers(Long userId, Pageable pageable) {
        return friendRepository.findFollowerByUser(userId, FriendStatusEnum.FOLLOWER, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Friend> findUserRequests(Long userId, Pageable pageable) {
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Friend> findUserProgress(Long userId, Pageable pageable) {
        return friendRepository.findProgressByUser(userId, FriendStatusEnum.PROGRESS, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Friend> findOne(Long firstUserId, Long secondUserId) {
        return Optional.ofNullable(friendRepository.findOne(firstUserId, secondUserId));
    }

    @Deprecated
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
                LOGGER.info("sendRequest friend failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + exist.get());
                return exist;
            }

        }

        return Optional.empty();
    }

    @Deprecated
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

    @Deprecated
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
                    LOGGER.info("accept sendRequest failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                    return request;
                }

            }

        }

        return Optional.empty();
    }

    @Deprecated
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
                    LOGGER.info("remove sendRequest successful");
                } else {
                    LOGGER.info("remove sendRequest failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                    return request;
                }

            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> requestFriend(Long actionUserId, Long otherUserId) {
        User action = new User(actionUserId);

        init(actionUserId, otherUserId);

        Optional<Friend> exist = findOne(first.getId(), second.getId());

        // todo create DTO
        if (!exist.isPresent()) {
            Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, action);
            return Optional.of(friendRepository.save(friend));
        } else {
            LOGGER.info("sendRequest friend failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + exist.get());
            return exist;
        }
    }

    @Transactional
    public Optional<Friend> removeRequest(Long actionUserId, Long otherUserId) {

        User action = new User(actionUserId);

        init(action.getId(), otherUserId);

        Optional<Friend> request = findOne(first.getId(), second.getId());
        if (request.isPresent()) {

            if (FriendStatusEnum.REQUEST == request.get().getStatus()
                    && request.get().getActionUser().getId().equals(action.getId())) {

                friendRepository.delete(request.get());
                LOGGER.info("remove sendRequest successful");
            } else {
                LOGGER.info("remove sendRequest failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Friend> acceptRequest(Long actionUserId, Long otherUserId) {

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
                LOGGER.info("accept sendRequest failed. Action =  " + action.getId() + ", other " + otherUserId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }



    @Transactional
    public Optional<Friend> removeFriend(Long actionUserId, Long otherUserId) {

        init(actionUserId, otherUserId);

        Optional<Friend> progress = findOne(first.getId(), second.getId());

        if (progress.isPresent()) {

            if (FriendStatusEnum.PROGRESS == progress.get().getStatus()) {
                Friend friend = progress.get();
                friend.setStatus(FriendStatusEnum.REQUEST);
                friend.setActionUser(getUserByIdFromFriendPk(otherUserId, friend));
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("remove friend failed. Action =  " + actionUserId + ", other " + otherUserId + ". Find row = " + progress.get());
                return progress;
            }

        } else {
            return Optional.empty();
        }
    }

}
