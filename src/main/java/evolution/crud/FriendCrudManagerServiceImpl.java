package evolution.crud;

import evolution.common.FriendStatusEnum;
import evolution.crud.api.FriendCrudManagerService;
import evolution.model.Friend;
import evolution.model.User;
import evolution.repository.FriendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
@Service
public class FriendCrudManagerServiceImpl implements FriendCrudManagerService {

    @Autowired
    private FriendRepository friendRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private User first;

    private User second;

    @Override
    public Page<Friend> findAll(Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        return friendRepository.findAll(pageable);
    }

    @Override
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Override
    public Optional<Friend> findOneFriend(Long user1, Long user2) {
        init(user1, user2);
        return friendRepository.findOneFriend(first.getId(), second.getId());
    }

    @Override
    public Page<Friend> findFollowerByUser(Long userId, FriendStatusEnum followerStatus, Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        return friendRepository.findFollowerByUser(userId, followerStatus, pageable);
    }

    @Override
    public Page<Friend> findRequestByUser(Long userId, FriendStatusEnum requestStatus, Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        return friendRepository.findRequestFromUser(userId, requestStatus, pageable);
    }

    @Override
    public Page<Friend> findProgressByUser(Long userId, FriendStatusEnum progressStatus, Integer page, Integer size) {
        Pageable pageable = getPageable(page, size);
        return friendRepository.findProgressByUser(userId, progressStatus, pageable);
    }

    @Override
    public List<Friend> findFollowerByUser(Long userId, FriendStatusEnum followerStatus) {
        return friendRepository.findFollowerByUser(userId, followerStatus);
    }

    @Override
    public List<Friend> findRequestByUser(Long userId, FriendStatusEnum requestStatus) {
        return friendRepository.findRequestFromUser(userId, requestStatus);
    }

    @Override
    public List<Friend> findProgressByUser(Long userId, FriendStatusEnum progressStatus) {
        return friendRepository.findProgressByUser(userId, progressStatus);
    }

    @Override
    @Transactional
    public Optional<Friend> sendRequestToFriend(Long senderId, Long recipientId) {
        User action = new User(senderId);

        init(senderId, recipientId);
        Optional<Friend> exist = friendRepository.findOneFriend(first.getId(), second.getId());

        if (!exist.isPresent()) {
            Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, action);
            return Optional.of(friendRepository.save(friend));
        } else {
            LOGGER.info("sendRequest friend failed. Action =  " + action.getId() + ", other " + recipientId + ". Find row = " + exist.get());
            return exist;
        }
    }

    @Override
    @Transactional
    public Optional<Friend> removeRequest(Long senderId, Long recipientId) {
        User action = new User(senderId);

        init(action.getId(), recipientId);

        Optional<Friend> request = findOneFriend(first.getId(), second.getId());

        if (request.isPresent()) {

            if (FriendStatusEnum.REQUEST == request.get().getStatus()
                    && request.get().getActionUser().getId().equals(action.getId())) {

                friendRepository.delete(request.get());
                LOGGER.info("remove sendRequest successful");
            } else {
                LOGGER.info("remove sendRequest failed. Action =  " + action.getId() + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Friend> removeFriend(Long senderId, Long recipientId) {
        init(senderId, recipientId);

        Optional<Friend> progress = findOneFriend(first.getId(), second.getId());

        if (progress.isPresent()) {

            if (FriendStatusEnum.PROGRESS == progress.get().getStatus()) {
                Friend friend = progress.get();
                friend.setStatus(FriendStatusEnum.REQUEST);
                friend.setActionUser(getUserByIdFromFriendPk(recipientId, friend));
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("remove friend failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + progress.get());
                return progress;
            }

        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Friend> acceptRequest(Long senderId, Long recipientId) {
        User action = new User(senderId);

        init(action, new User(recipientId));

        Optional<Friend> request = findOneFriend(first.getId(), second.getId());
        if (request.isPresent()) {

            if (request.get().getStatus() == FriendStatusEnum.REQUEST && !action.getId().equals(request.get().getActionUser().getId())) {
                Friend friend = request.get();
                friend.setStatus(FriendStatusEnum.PROGRESS);
                friend.setActionUser(action);
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("accept sendRequest failed. Action =  " + action.getId() + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    @Value("${model.friend.maxfetch}")
    private Integer friendMaxFetch;

    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return getPageableForRestService(page, size,
                this.friendMaxFetch);
    }

    @Override
    public Pageable getPageable(Integer page, Integer size, String sort, List<String> sortProperties) {
        throw new UnsupportedOperationException("use  public Pageable getPageable(Integer page, Integer size) ");
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        throw new UnsupportedOperationException("sort not supported");
    }

    private void init(Long senderRequest, Long someUser) {
        init(new User(senderRequest), new User(someUser));
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

    private User getUserByIdFromFriendPk(Long userId, Friend friend) {
        if (friend.getPk().getFirst().getId().equals(userId))
            return friend.getPk().getFirst();
        else
            return friend.getPk().getSecond();
    }
}
