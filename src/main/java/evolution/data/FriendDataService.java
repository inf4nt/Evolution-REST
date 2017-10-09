package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import evolution.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Infant on 09.10.2017.
 */
@Service
public class FriendDataService {

    private final FriendRepository friendRepository;

    private static User first;

    private static User second;


    @Autowired
    public FriendDataService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    private void init(User senderRequest, User someUser) {
        if (senderRequest.getId() > someUser.getId()) {
            first = senderRequest;
            second = someUser;
        } else {
            first = someUser;
            second = senderRequest;
        }
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

    public Optional<Friend> acceptReqeust(User senderRequest, User someUser) {
        init(senderRequest, someUser);
        Optional<Friend> request = findByAllParams(first.getId(), second.getId(), senderRequest.getId(), FriendStatusEnum.REQUEST);
        if (request.isPresent()) {
            // ok set status to progress
            Friend friend = request.get();
            friend.setActionUser(senderRequest);
            friend.setStatus(FriendStatusEnum.PROGRESS);
            return Optional.of(friendRepository.save(friend));
        } else {
            return Optional.empty();
        }
    }


    @Transactional
    public boolean isExist(Long first, Long second) {
        return friendRepository.isExist(first, second) != null;
    }

    @Transactional
    public boolean isExist(User first, User second) {
        return friendRepository.isExist(first.getId(), second.getId()) != null;
    }

    @Transactional
    public Optional<Friend> findByAllParams(Long firstUserId, Long secondUserId,
                                            Long actionUserId, FriendStatusEnum status) {
        return Optional.ofNullable(friendRepository.findByAllParams(firstUserId, secondUserId, actionUserId, status));
    }

}
