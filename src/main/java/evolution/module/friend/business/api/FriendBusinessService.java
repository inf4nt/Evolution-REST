package evolution.module.friend.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.friend.common.FriendActionEnum;
import evolution.module.friend.dto.FriendActionDTO;
import evolution.module.friend.dto.FriendDTO;
import evolution.module.friend.dto.FriendDTOLazy;
import evolution.module.friend.dto.FriendResultActionDTO;
import evolution.module.friend.model.Friend;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FriendBusinessService {

    BusinessServiceExecuteResult<FriendResultActionDTO> acceptRequest(FriendActionDTO friendActionDTO);

    BusinessServiceExecuteResult<FriendResultActionDTO> deleteFriend(FriendActionDTO friendActionDTO);

    BusinessServiceExecuteResult<FriendResultActionDTO> deleteRequest(FriendActionDTO friendActionDTO);

    BusinessServiceExecuteResult<FriendResultActionDTO> sendRequestToFriend(FriendActionDTO friendActionDTO);

    Optional<FriendDTO> findOne(Long first, Long second);

    List<FriendDTOLazy> findAll2();

    Page<FriendDTOLazy> findAll2(Integer page, Integer size);

    List<FriendDTOLazy> findFriends2(Long iam);

    Page<FriendDTOLazy> findFriends2(Long iam, Integer page, Integer size);

    List<FriendDTOLazy> findFollowers2(Long iam);

    Page<FriendDTOLazy> findFollowers2(Long iam, Integer page, Integer size);

    List<FriendDTOLazy> findRequests2(Long iam);

    Page<FriendDTOLazy> findRequests2(Long iam, Integer page, Integer size);

    List<FriendDTO> findAll();

    Page<FriendDTO> findAll(Integer page, Integer size);

    List<FriendDTO> findFriends(Long iam);

    Page<FriendDTO> findFriends(Long iam, Integer page, Integer size);

    List<FriendDTO> findFollowers(Long iam);

    Page<FriendDTO> findFollowers(Long iam, Integer page, Integer size);

    List<FriendDTO> findRequests(Long iam);

    Page<FriendDTO> findRequests(Long iam, Integer page, Integer size);

    BusinessServiceExecuteResult<FriendResultActionDTO> action(FriendActionDTO actionDTO);

    FriendActionEnum getNextAction(Friend friend);

    BusinessServiceExecuteResult<FriendResultActionDTO> findNextAction(Long first, Long second);

    BusinessServiceExecuteResult<FriendResultActionDTO> findNextAction(Long second);

    Page<FriendDTO> findRandomFriends(Long user);
}
