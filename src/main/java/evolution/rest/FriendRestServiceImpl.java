package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.FriendBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
import evolution.rest.api.FriendRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class FriendRestServiceImpl implements FriendRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FriendBusinessService friendBusinessService;

    @Autowired
    public FriendRestServiceImpl(FriendBusinessService friendBusinessService) {
        this.friendBusinessService = friendBusinessService;
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findAll2() {
        List<FriendDTOFull> list = friendBusinessService.findAll2();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<FriendDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findAll(page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findAll2(Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findAll2(page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findFollowers(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findRequests(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findFriends(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserFollower2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findFollowers2(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserRequest2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findRequests2(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserProgress2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findFriends2(userId, page, size);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserFollower(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findFollowers2(userId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserRequest(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findRequests2(userId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserProgress(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findFriends2(userId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<FriendDTOFull> sendRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendDTOFull> b = friendBusinessService.sendRequestToFriend(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(201).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendDTOFull> removeRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendDTOFull> b = friendBusinessService.deleteRequest(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.ok().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendDTOFull> removeFriend(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendDTOFull> b = friendBusinessService.deleteFriend(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(200).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendDTOFull> acceptRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendDTOFull> b = friendBusinessService.acceptRequest(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(200).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        }

        return ResponseEntity.noContent().build();
    }
}
