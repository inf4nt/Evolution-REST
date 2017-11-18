package evolution.rest.api;

import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 14.10.2017.
 */
public interface FriendRestService {

    ResponseEntity<List<FriendDTO>> findAll();

    ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size);

    ResponseEntity<List<FriendDTOFull>> findAll2();

    ResponseEntity<Page<FriendDTOFull>> findAll2(Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserFollower2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserRequest2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserProgress2(Long userId, Integer page, Integer size);

    ResponseEntity<List<FriendDTOFull>> findUserFollower(Long userId);

    ResponseEntity<List<FriendDTOFull>> findUserRequest(Long userId);

    ResponseEntity<List<FriendDTOFull>> findUserProgress(Long userId);

    ResponseEntity<FriendDTOFull> sendRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendDTOFull> removeRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendDTOFull> removeFriend(FriendActionDTO friendDTO);

    ResponseEntity<FriendDTOFull> acceptRequest(FriendActionDTO friendDTO);
}
