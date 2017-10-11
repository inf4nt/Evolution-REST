package evolution.controller;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.data.FriendDataService;
import evolution.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 09.10.2017.
 */
@RestController
@RequestMapping(value = "/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendDataService friendDataService;

    @Autowired
    public FriendRestController(FriendDataService friendDataService) {
        this.friendDataService = friendDataService;
    }

    @GetMapping(value = "/status/{userId}/{status}")
    public ResponseEntity<List<Friend>> findByStatus(@PathVariable Long userId, @PathVariable String status) {
        List<Friend> list = friendDataService.findByStatus(FriendStatusEnum.valueOf(status.toUpperCase()), userId);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/action/{otherId}/{action}")
    public ResponseEntity<Friend> actionFriend(@PathVariable String action, @PathVariable Long otherId) {
        Optional<Friend> optional = friendDataService.actionFriend(FriendActionEnum.valueOf(action.toUpperCase()), otherId);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(500).build();
        }
    }


//    @GetMapping(value = "/{id}/follower")
//    public List<Friend> findFollowerByUser(@PathVariable Long id) {
//        return friendRepository.findFollowerByUser(id, FriendStatusEnum.REQUEST);
//    }
//
//    @GetMapping(value = "/{id}/request")
//    public List<Friend> findRequestFromUser(@PathVariable Long id) {
//        return friendRepository.findRequestFromUser(id, FriendStatusEnum.REQUEST);
//    }
//
//    @GetMapping(value = "/{id}/progress")
//    public List<Friend> finProgressByUser(@PathVariable Long id) {
//        return friendRepository.findProgressByUser(id, FriendStatusEnum.PROGRESS);
//    }

//    @GetMapping(value = "/action/{otherUserId}/accept")
//    public ResponseEntity<Friend> actionFriendAccept(@PathVariable Long otherUserId) {
//        Optional<Friend> friend = friendDataService.acceptRequest(otherUserId);
//        if (friend.isPresent()) {
//            return ResponseEntity.ok(friend.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
//    }
//
//    @GetMapping(value = "/action/{otherUserId}/remove")
//    public ResponseEntity<Friend> actionFriendRemove(@PathVariable Long otherUserId) {
//        Optional<Friend> friend = friendDataService.removeFriend(otherUserId);
//        if (friend.isPresent()) {
//            return ResponseEntity.ok(friend.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
//    }
//
//    @GetMapping(value = "/action/{otherUserId}/remove_request")
//    public ResponseEntity<Friend> actionRemoveRequest(@PathVariable Long otherUserId) {
//        Optional<Friend> friend = friendDataService.removeRequest(otherUserId);
//        if (friend.isPresent()) {
//            return ResponseEntity.ok(friend.get());
//        } else {
//            return ResponseEntity.status(417).build();
//        }
//    }


//
//    @GetMapping(value = "/action/{firstUserId}/{secondUserId}/{actionUserId}")
//    public Object actionFriend(@PathVariable Long firstUserId, @PathVariable Long secondUserId, @PathVariable Long actionUserId) {
//        return friendDataService.acceptRequest(firstUserId, secondUserId, actionUserId);
//    }
}
