package evolution.rest.api;

import evolution.dto.modelOld.FriendActionDTO;
import evolution.dto.modelOld.FriendDTO;
import evolution.dto.modelOld.FriendDTOFull;
import evolution.dto.modelOld.FriendResultActionDTO;
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

    ResponseEntity<List<FriendDTOFull>> findAll2();

    ResponseEntity<Page<FriendDTOFull>> findAll2(Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserFollower2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserRequest2(Long userId, Integer page, Integer size);

    ResponseEntity<Page<FriendDTOFull>> findUserProgress2(Long userId, Integer page, Integer size);

    ResponseEntity<List<FriendDTOFull>> findUserFollower(Long userId);

    ResponseEntity<List<FriendDTOFull>> findUserRequest(Long userId);

    ResponseEntity<List<FriendDTOFull>> findUserProgress(Long userId);

    ResponseEntity<FriendResultActionDTO> sendRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> removeRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> removeFriend(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> acceptRequest(FriendActionDTO friendDTO);

    ResponseEntity<FriendResultActionDTO> action(FriendActionDTO actionDTO);

    ResponseEntity<FriendResultActionDTO> findNexAction(Long first, Long second);

    ResponseEntity<FriendResultActionDTO> findNexAction(Long second);

    ResponseEntity<Page<FriendDTO>> findRandomProgress(Long user);
}
