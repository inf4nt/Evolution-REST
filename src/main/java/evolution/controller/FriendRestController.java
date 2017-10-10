package evolution.controller;

import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.data.FriendDataService;
import evolution.data.FriendRepository;
import evolution.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Infant on 09.10.2017.
 */
@RestController
@RequestMapping(value = "/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendRepository friendRepository;

    private final FriendDataService friendDataService;

    @Autowired
    public FriendRestController(FriendRepository friendRepository, FriendDataService friendDataService) {
        this.friendRepository = friendRepository;
        this.friendDataService = friendDataService;
    }

    @GetMapping(value = "/{id}/follower")
    public Friend findFollowerByUser(@PathVariable Long id) {
        return friendRepository.findFollowerByUser(id, FriendStatusEnum.REQUEST);
    }

    @GetMapping(value = "/{id}/request")
    public Friend findRequestFromUser(@PathVariable Long id) {
        return friendRepository.findRequestFromUser(id, FriendStatusEnum.REQUEST);
    }

    @GetMapping(value = "/{id}/progress")
    public Friend finProgressByUser(@PathVariable Long id) {
        return friendRepository.findProgressByUser(id, FriendStatusEnum.PROGRESS);
    }


    @GetMapping(value = "/action/{otherUserId}/accept")
    public ResponseEntity<Friend> actionFriendAccept(@PathVariable Long otherUserId) {
        Optional<Friend> friend = friendDataService.acceptRequest(otherUserId);
        if(friend.isPresent()) {
            return ResponseEntity.ok(friend.get());
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @GetMapping(value = "/action/{otherUserId}/remove")
    public ResponseEntity<Friend> actionFriendRemove(@PathVariable Long otherUserId) {
        Optional<Friend> friend = friendDataService.removeFriend(otherUserId);
        if(friend.isPresent()) {
            return ResponseEntity.ok(friend.get());
        } else {
            return ResponseEntity.status(417).build();
        }
    }


//
//    @GetMapping(value = "/action/{firstUserId}/{secondUserId}/{actionUserId}")
//    public Object actionFriend(@PathVariable Long firstUserId, @PathVariable Long secondUserId, @PathVariable Long actionUserId) {
//        return friendDataService.acceptRequest(firstUserId, secondUserId, actionUserId);
//    }
}
