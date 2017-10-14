package evolution.rest;

import evolution.data.FriendDataService;
import evolution.model.Friend;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 14.10.2017.
 */
@Service
public class FriendRestServiceImpl implements FriendRestService {

    private final SecuritySupportService securitySupportService;

    private final FriendDataService friendDataService;

    @Autowired
    public FriendRestServiceImpl(SecuritySupportService securitySupportService, FriendDataService friendDataService) {
        this.securitySupportService = securitySupportService;
        this.friendDataService = friendDataService;
    }

    @Override
    public ResponseEntity<List<Friend>> findUserFollower(Long userId, Integer page, Integer size) {
        List<Friend> list;
        if (page == null || size == null) {
            list = friendDataService.findUserFollowers(userId);
        } else {
            list = friendDataService.findUserFollowers(userId, new PageRequest(page, size)).getContent();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<Friend>> findUserRequest(Long userId, Integer page, Integer size) {
        List<Friend> list;
        if (page == null || size == null) {
            list = friendDataService.findUserRequests(userId);
        } else {
            list = friendDataService.findUserRequests(userId, new PageRequest(page, size)).getContent();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<Friend>> findUserProgress(Long userId, Integer page, Integer size) {
        List<Friend> list;
        if (page == null || size == null) {
            list = friendDataService.findUserProgress(userId);
        } else {
            list = friendDataService.findUserProgress(userId, new PageRequest(page, size)).getContent();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
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
    public ResponseEntity<List<Friend>> findAll(Integer page, Integer size) {
        List<Friend> list;
        if (page == null || size == null) {
            list = friendDataService.findAll();
        } else {
            list = friendDataService.findAll(new PageRequest(page, size)).getContent();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
