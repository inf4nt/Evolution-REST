package evolution.rest;

import evolution.data.FriendDataService;
import evolution.helper.HelperDataService;
import evolution.helper.HelperRestService;
import evolution.model.Friend;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class FriendRestServiceImpl implements FriendRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FriendDataService friendDataService;

    private final HelperDataService helperDataService;

    private final HelperRestService<Friend> helperRestService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public FriendRestServiceImpl(FriendDataService friendDataService, HelperDataService helperDataService, HelperRestService<Friend> helperRestService, SecuritySupportService securitySupportService) {
        this.friendDataService = friendDataService;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public ResponseEntity<Page<Friend>> findUserFollower(Long userId, Integer page, Integer size) {
        Pageable pageable = helperDataService.getPageableForFriend(page, size);
        Page<Friend> p = friendDataService.findUserFollowers(userId, pageable);
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<Page<Friend>> findUserRequest(Long userId, Integer page, Integer size) {
        Pageable pageable = helperDataService.getPageableForFriend(page, size);
        Page<Friend> p = friendDataService.findUserRequests(userId, pageable);
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<Page<Friend>> findUserProgress(Long userId, Integer page, Integer size) {
        Pageable pageable = helperDataService.getPageableForFriend(page, size);
        Page<Friend> p = friendDataService.findUserProgress(userId, pageable);
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<Friend> sendRequest(Long userId) {
        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Friend> optional = friendDataService.requestFriend(actionUser.getId(), userId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<Friend> removeRequest(Long userId) {
        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Friend> optional = friendDataService.removeRequest(actionUser.getId(), userId);
        if(!optional.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(optional.get());
        }
    }

    @Override
    public ResponseEntity<Friend> removeFriend(Long userId) {
        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Friend> optional = friendDataService.removeFriend(actionUser.getId(), userId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<Friend> acceptRequest(Long userId) {
        User actionUser = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Friend> optional = friendDataService.acceptRequest(actionUser.getId(), userId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<Page<Friend>> findAll(Integer page, Integer size) {
        Pageable pageable = helperDataService.getPageableForFriend(page, size);
        Page<Friend> p = friendDataService.findAll(pageable);
        return helperRestService.getResponseForPage(p);
    }
}
