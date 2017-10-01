package evolution.controller;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.common.ServiceStatus;
import evolution.data.FriendsDataService;
import evolution.model.friend.Friends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Infant on 26.09.2017.
 */

@RestController
@RequestMapping(value = "/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendsDataService friendsDataService;

    @Autowired
    public FriendRestController(FriendsDataService friendsDataService) {
        this.friendsDataService = friendsDataService;
    }

    @GetMapping(value = "/user/{userId}/{status}")
    public ResponseEntity<List<Friends>> findFriendsByUserAndStatus(@PathVariable Long userId,
                                                                    @PathVariable String status) {
        List<Friends> list = friendsDataService.findFriendsByStatusAndUser(userId, FriendStatusEnum.valueOf(status.toUpperCase()));

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/status/{user1}/{user2}")
    public ResponseEntity<Friends> findFriendStatusByUsers(@PathVariable Long user1,
                                                          @PathVariable Long user2) {
        Friends friends = friendsDataService.findFriendStatusByUsers(user1, user2);
        return ResponseEntity.ok(friends);
    }

    @PostMapping(value = "/action/user/{friendId}/{action}")
    public ResponseEntity<HttpStatus> actionForFriends(@PathVariable Long friendId,
                                                       @PathVariable String action) {
        try {

            ServiceStatus result = friendsDataService.actionFriends(friendId, FriendActionEnum.valueOf(action.toUpperCase()));

            if (result == ServiceStatus.TRUE)
                return ResponseEntity.ok().build();
            else if (result == ServiceStatus.EXPECTATION_FAILED || result == ServiceStatus.FALSE)
                return ResponseEntity.status(417).build();
            else if (result == ServiceStatus.AUTH_NOT_FOUND)
                return ResponseEntity.status(401).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Friends>> findAll() {
        List<Friends> list = friendsDataService.findAll();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(list);
    }

    // todo: replace to POST after testing
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/action/user/{user1}/{user2}/{action}/get")
    public ResponseEntity<HttpStatus> actionForFriendsAdmin(@PathVariable Long user1,
                                                            @PathVariable Long user2,
                                                            @PathVariable String action) {
        try {

            ServiceStatus result = friendsDataService.actionFriendsAdminService(user1, user2, FriendActionEnum.valueOf(action.toUpperCase()));

            if (result == ServiceStatus.TRUE)
                return ResponseEntity.ok().build();
            else if (result == ServiceStatus.EXPECTATION_FAILED || result == ServiceStatus.FALSE)
                return ResponseEntity.status(417).build();
            else if (result == ServiceStatus.AUTH_NOT_FOUND)
                return ResponseEntity.status(401).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
