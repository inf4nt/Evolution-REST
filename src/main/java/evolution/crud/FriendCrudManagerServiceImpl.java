package evolution.crud;

import evolution.common.FriendActionEnum;
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
        return friendRepository.findFollowerByUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND, pageable);
    }

    @Override
    public Page<Friend> findRequestByUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findRequestFromUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND, pageable);
    }

    @Override
    public Page<Friend> findProgressByUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findProgressByUser(userId, FriendActionEnum.ACCEPT_REQUEST, pageable);
    }

    @Override
    public List<Friend> findFollowerByUser(Long userId) {
        return friendRepository.findFollowerByUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND);
    }

    @Override
    public List<Friend> findRequestByUser(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND);
    }

    @Override
    public List<Friend> findProgressByUser(Long userId) {
        return friendRepository.findProgressByUser(userId, FriendActionEnum.ACCEPT_REQUEST);
    }

    @Override
    public Page<Friend> findRequestFromUser(Long userId, Integer page, Integer size) {
        Pageable pageable = getPageableForRestService(page, size,
                this.friendMaxFetch);
        return friendRepository.findRequestFromUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND, pageable);
    }

    @Override
    public List<Friend> findRequestFromUser(Long userId) {
        return friendRepository.findRequestFromUser(userId, FriendActionEnum.SEND_REQUEST_TO_FRIEND);
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

            Friend friend = new Friend(first, second, FriendActionEnum.SEND_REQUEST_TO_FRIEND, action);
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

            boolean m1 = FriendActionEnum.SEND_REQUEST_TO_FRIEND == request.get().getAction()
                    && request.get().getActionUser().getId().equals(senderId);

            boolean m2 = FriendActionEnum.DELETE_FRIEND == request.get().getAction()
                    && request.get().getActionUser().getId().equals(recipientId);

            if (m1 || m2) {
                friendRepository.delete(request.get());
                LOGGER.info("remove sendRequest successful");
                return Optional.of(new Friend(FriendActionEnum.NO_ACTION));
            } else {
                LOGGER.info("remove sendRequest failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.of(new Friend(FriendActionEnum.NO_ACTION));
    }

    @Override
    @Transactional
    public Optional<Friend> removeFriend(Long senderId, Long recipientId) {
        init(senderId, recipientId);
        User action;
        Optional<Friend> progress = findOneFriend(firstId, secondId);

        if (progress.isPresent()) {

            if (FriendActionEnum.ACCEPT_REQUEST == progress.get().getAction()) {
                Friend friend = progress.get();
                friend.setAction(FriendActionEnum.DELETE_FRIEND);

                action = getUserByIdFromFriendPk(senderId, friend);

                friend.setActionUser(action);

                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("remove second failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + progress.get());
                return progress;
            }

        } else {
            return Optional.of(new Friend(FriendActionEnum.NO_ACTION));
        }
    }

    @Override
    @Transactional
    public Optional<Friend> acceptRequest(Long senderId, Long recipientId) {
        User action;

        init(senderId, recipientId);

        Optional<Friend> request = findOneFriend(firstId, secondId);
        if (request.isPresent()) {

            boolean m1 = request.get().getActionUser().getId().equals(senderId)
                    && request.get().getAction() == FriendActionEnum.DELETE_FRIEND;

            boolean m2 = request.get().getActionUser().getId().equals(recipientId)
                    && request.get().getAction() == FriendActionEnum.SEND_REQUEST_TO_FRIEND;

            if (m1 || m2) {
                Friend friend = request.get();
                friend.setAction(FriendActionEnum.ACCEPT_REQUEST);

                action = getUserByIdFromFriendPk(senderId, friend);

                friend.setActionUser(action);
                return Optional.of(friendRepository.save(friend));
            } else {
                LOGGER.info("accept sendRequest failed. Action =  " + senderId + ", other " + recipientId + ". Find row = " + request.get());
                return request;
            }

        }

        return Optional.of(new Friend(FriendActionEnum.NO_ACTION));
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
