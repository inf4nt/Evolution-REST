package evolution.rest;

import evolution.dto.model.FriendDTO;
import evolution.rest.api.FriendRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class FriendRestServiceImpl implements FriendRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public ResponseEntity<Page<FriendDTO>> findUserFollower(Long userId, Integer page, Integer size) {
//        Pageable pageable = helperDataService.getPageableForFriend(page, size);
//        Page<Friend> p = friendDataService.findUserFollowers(userId, pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserRequest(Long userId, Integer page, Integer size) {
//        Pageable pageable = helperDataService.getPageableForFriend(page, size);
//        Page<Friend> p = friendDataService.findUserRequests(userId, pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findUserProgress(Long userId, Integer page, Integer size) {
//        Pageable pageable = helperDataService.getPageableForFriend(page, size);
//        Page<Friend> p = friendDataService.findUserProgress(userId, pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<FriendDTO> sendRequest(Long userId) {
//        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
//        Optional<Friend> optional = friendDataService.requestFriend(actionUser.getId(), userId);
//        if (optional.isPresent()) {
//            return ResponseEntity.ok(optional.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<FriendDTO> removeRequest(Long userId) {
//        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
//        Optional<Friend> optional = friendDataService.removeRequest(actionUser.getId(), userId);
//        if(!optional.isPresent()) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.ok(optional.get());
//        }
        return null;
    }

    @Override
    public ResponseEntity<FriendDTO> removeFriend(Long userId) {
//        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
//        Optional<Friend> optional = friendDataService.removeFriend(actionUser.getId(), userId);
//        if (optional.isPresent()) {
//            return ResponseEntity.ok(optional.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<FriendDTO> acceptRequest(Long userId) {
//        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
//        Optional<Friend> optional = friendDataService.acceptRequest(actionUser.getId(), userId);
//        if (optional.isPresent()) {
//            return ResponseEntity.ok(optional.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<Page<FriendDTO>> findAll(Integer page, Integer size) {
//        Pageable pageable = helperDataService.getPageableForFriend(page, size);
//        Page<Friend> p = friendDataService.findAll(pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }
}
