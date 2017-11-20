package evolution.crud;

import evolution.common.FriendStatusEnum;
import evolution.crud.api.FriendCrudManagerService;
import evolution.model.Friend;
import evolution.model.User;
import evolution.repository.FriendRepository;
import evolution.repository.UserRepository;
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

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    private Long firstId;

    private Long secondId;

    @Value("${model.second.maxfetch}")
    private Integer friendMaxFetch;

    @Autowired
    public FriendCrudManagerServiceImpl(FriendRepository friendRepository,
                                        UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Friend> findAll(Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findAll(pageable);
    }

    @Override
    public List<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Override
    public Optional<Friend> findOneFriend(Long user1, Long user2) {
        init(user1, user2);
        return friendRepository.findOneFriend(firstId, secondId);
    }

    @Override
    public Page<Friend> findFollowerByUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findFollowerByUser(userId, FriendStatusEnum.FOLLOWER, pageable);
    }

    @Override
    public Page<Friend> findRequestByUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST, pageable);
    }

    @Override
    public Page<Friend> findProgressByUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findProgressByUser(userId, FriendStatusEnum.PROGRESS, pageable);
    }

    @Override
    public List<Friend> findFollowerByUser(Long userId) {
        return friendRepository.findFollowerByUser(userId, FriendStatusEnum.FOLLOWER);
    }

    @Override
    public List<Friend> findRequestByUser(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST);
    }

    @Override
    public List<Friend> findProgressByUser(Long userId) {
        return friendRepository.findProgressByUser(userId, FriendStatusEnum.PROGRESS);
    }

    @Override
    public Page<Friend> findRequestFromUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST, pageable);
    }

    @Override
    public List<Friend> findRequestFromUser(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendStatusEnum.REQUEST);
    }

    @Override
    @Transactional
    public Optional<Friend> sendRequestToFriend(Long senderId, Long recipientId) {
        User action = null;

        init(senderId, recipientId);
        Optional<Friend> exist = friendRepository.findOneFriend(firstId, secondId);
        Optional<User> of = userRepository.findOneUserById(firstId);
        Optional<User> os = userRepository.findOneUserById(secondId);

        if (!of.isPresent() || !os.isPresent()) {
            return exist;
        }

        if (!exist.isPresent()) {

            User first = of.get();
            User second = os.get();

            if (first.getId().equals(senderId)) {
                action = first;
            }

            if (second.getId().equals(senderId)) {
                action = second;
            }

            Friend friend = new Friend(first, second, FriendStatusEnum.REQUEST, action);
            return Optional.of(friendRepository.save(friend));
        } else {
            LOGGER.info("sendRequest second failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + exist.get());
            return exist;
        }
    }

    @Override
    @Transactional
    public Optional<Friend> removeRequest(Long senderId, Long recipientId) {
        init(senderId, recipientId);

        Optional<Friend> request = findOneFriend(firstId, secondId);

        if (request.isPresent()) {

            if (FriendStatusEnum.REQUEST == request.get().getStatus()
                    && request.get().getActionUser().getId().equals(senderId)) {
                friendRepository.delete(request.get());
                LOGGER.info("remove sendRequest successful");
                return Optional.of(new Friend());
            } else {
                LOGGER.info("remove sendRequest failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Friend> removeFriend(Long senderId, Long recipientId) {
        init(senderId, recipientId);
        User action;
        Optional<Friend> progress = findOneFriend(firstId, secondId);

        if (progress.isPresent()) {

            if (FriendStatusEnum.PROGRESS == progress.get().getStatus()) {
                Friend friend = progress.get();
                friend.setStatus(FriendStatusEnum.REQUEST);

                action = getUserByIdFromFriendPk(senderId, friend);

                friend.setActionUser(action);

                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("remove second failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + progress.get());
                return progress;
            }

        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Friend> acceptRequest(Long senderId, Long recipientId) {
        User action;

        init(senderId, recipientId);

        Optional<Friend> request = findOneFriend(firstId, secondId);
        if (request.isPresent()) {

            if (request.get().getStatus() == FriendStatusEnum.REQUEST && !senderId.equals(request.get().getActionUser().getId())) {
                Friend friend = request.get();
                friend.setStatus(FriendStatusEnum.PROGRESS);

                action = getUserByIdFromFriendPk(senderId, friend);

                friend.setActionUser(action);
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("accept sendRequest failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.empty();
    }

    private void init(Long senderOrAction, Long recipient) {
        if (senderOrAction > recipient) {
            firstId = senderOrAction;
            secondId = recipient;
        } else {
            firstId = recipient;
            secondId = senderOrAction;
        }
    }

    private User getUserByIdFromFriendPk(Long userId, Friend friend) {
        if (friend.getPk().getFirst().getId().equals(userId))
            return friend.getPk().getFirst();
        else if (friend.getPk().getSecond().getId().equals(userId))
            return friend.getPk().getSecond();
        else
          throw new UnsupportedOperationException("Friend primary key not have user by id " + userId);
    }
}
