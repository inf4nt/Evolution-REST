package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.FriendBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.FriendActionEnum;
import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
import evolution.dto.model.FriendResultActionDTO;
import evolution.rest.api.FriendRestService;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class FriendRestServiceImpl implements FriendRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FriendBusinessService friendBusinessService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public FriendRestServiceImpl(FriendBusinessService friendBusinessService,
                                 SecuritySupportService securitySupportService) {
        this.friendBusinessService = friendBusinessService;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findAll2() {
        List<FriendDTOFull> list = friendBusinessService.findAll2();
        return response(list);
    }

    @Override
    public ResponseEntity<FriendDTO> findOne(Long first, Long second) {
        Optional<FriendDTO> op = friendBusinessService.findOne(first, second);
        return response(op);
    }

    @Override
    public ResponseEntity<List<FriendDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findAll(page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findAll2(Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findAll2(page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findFollowers(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findRequests(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size) {
        Page<FriendDTO> p = friendBusinessService.findFriends(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserFollower2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findFollowers2(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserRequest2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findRequests2(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<Page<FriendDTOFull>> findUserProgress2(Long userId, Integer page, Integer size) {
        Page<FriendDTOFull> p = friendBusinessService.findFriends2(userId, page, size);
        return response(p);
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserFollower(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findFollowers2(userId);
        return response(list);
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserRequest(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findRequests2(userId);
        return response(list);
    }

    @Override
    public ResponseEntity<List<FriendDTOFull>> findUserProgress(Long userId) {
        List<FriendDTOFull> list = friendBusinessService.findFriends2(userId);
        return response(list);
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> sendRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.sendRequestToFriend(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(201).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> removeRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.deleteRequest(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObjectOptional().get());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> removeFriend(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.deleteFriend(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(200).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> acceptRequest(FriendActionDTO friendDTO) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.acceptRequest(friendDTO);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(200).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED) {
            return ResponseEntity.status(417).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> action(FriendActionDTO actionDTO) {
        if (actionDTO.getAction() == FriendActionEnum.ACCEPT_REQUEST) {
            return acceptRequest(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.DELETE_FRIEND) {
            return removeFriend(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.DELETE_REQUEST) {
            return removeRequest(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.SEND_REQUEST_TO_FRIEND) {
            return sendRequest(actionDTO);
        }


        return ResponseEntity.status(500).build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> findNexAction(Long first, Long second) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.findNextAction(first, second);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObjectOptional().get());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<FriendResultActionDTO> findNexAction(Long second) {
        BusinessServiceExecuteResult<FriendResultActionDTO> b = friendBusinessService.findNextAction(second);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObjectOptional().get());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findRandomProgress(Long user) {
        Page<FriendDTO> b = friendBusinessService.findRandomFriends(user);
        if (b.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(b);
        }
    }
}
