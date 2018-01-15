package evolution.module.friend.rest.api;

import evolution.module.friend.dto.FriendActionDTO;
import evolution.module.friend.dto.FriendDTO;
import evolution.module.friend.dto.FriendDTOLazy;
import evolution.module.friend.dto.FriendResultActionDTO;
import evolution.rest.api.AbstractRestService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 14.10.2017.
 */
public interface FriendRestService extends AbstractRestService {

    ResponseEntity<FriendDTO> findOne(Long first, Long second);

    ResponseEntity<List<FriendDTO>> findAll();

    ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size);

    ResponseEntity<List<FriendDTOLazy>> findAll2();

    ResponseEntity<Page<FriendDTOLazy>> findAll2(Integer page, Integer size);

    ResponseEntity<Page<FriendDTOLazy>> findUserFollower2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOLazy>> findUserRequest2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOLazy>> findUserProgress2(Long userId, Integer page, Integer size);

    ResponseEntity<List<FriendDTOLazy>> findUserFollower(Long userId);

    ResponseEntity<List<FriendDTOLazy>> findUserRequest(Long userId);

    ResponseEntity<List<FriendDTOLazy>> findUserProgress(Long userId);

    ResponseEntity<FriendResultActionDTO> sendRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> removeRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> removeFriend(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> acceptRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> action(FriendActionDTO actionDTO);

    ResponseEntity<FriendResultActionDTO> findNexAction(Long first, Long second);

    ResponseEntity<FriendResultActionDTO> findNexAction(Long second);

    ResponseEntity<Page<FriendDTO>> findRandomProgress(Long user);
}
