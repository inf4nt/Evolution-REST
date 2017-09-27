package evolution.controller;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.data.FriendsDataService;
import evolution.model.friend.Friends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Infant on 26.09.2017.
 */

@RestController
@RequestMapping(value = "/rest/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendsDataService friendsDataService;

    @Autowired
    public FriendRestController(FriendsDataService friendsDataService) {
        this.friendsDataService = friendsDataService;
    }

    @GetMapping(value = "/user/{user_id}/{status}")
    public ResponseEntity findFriendsByUserAndStatus(@PathVariable(value = "user_id") Long userId,
                                                     @PathVariable String status) {
        List<Friends> list = friendsDataService.findFriendsByStatusAndUser(userId, FriendStatusEnum.valueOf(status.toUpperCase()));
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/user/{friend_id}/{action}")
    public ResponseEntity actionForFriends(@PathVariable(value = "friend_id") Long friendId, String action) {
        try {
            if (friendsDataService.actionFriends(friendId, FriendActionEnum.valueOf(action.toUpperCase()).getId()))
                return ResponseEntity.ok().build();
            else
                return ResponseEntity.status(417).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<Friends> list = friendsDataService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }


}
