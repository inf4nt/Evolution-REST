package evolution.rest.api;

import evolution.dto.model.FriendDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * Created by Infant on 14.10.2017.
 */
public interface FriendRestService {

    ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size);

    ResponseEntity<FriendDTO> sendRequest(Long userId);

    ResponseEntity<FriendDTO> removeRequest(Long userId);

    ResponseEntity<FriendDTO> removeFriend(Long userId);

    ResponseEntity<FriendDTO> acceptRequest(Long userId);

    ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size);
}
