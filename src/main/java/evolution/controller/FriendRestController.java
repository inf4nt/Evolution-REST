package evolution.controller;


import evolution.model.Friend;
import evolution.rest.FriendRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by Infant on 09.10.2017.
 */
@RestController
@RequestMapping(value = "/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendRestService friendRestService;

    @Autowired
    public FriendRestController(FriendRestService friendRestService) {
        this.friendRestService = friendRestService;
    }

    @GetMapping
    public ResponseEntity<List<Friend>> findAll(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size) {
        return friendRestService.findAll(page, size);
    }

    @GetMapping(value = "/{userId}/status/follower")
    public ResponseEntity<List<Friend>> findUserFollowers(@PathVariable Long userId,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size) {
        return friendRestService.findUserFollower(userId, page, size);
    }


    @GetMapping(value = "/{userId}/status/request")
    public ResponseEntity<List<Friend>> findUserRequests(@PathVariable Long userId,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {
        return friendRestService.findUserRequest(userId, page, size);
    }


    @GetMapping(value = "/{userId}/status/progress")
    public ResponseEntity<List<Friend>> findUserProgress(@PathVariable Long userId,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {

        return friendRestService.findUserProgress(userId, page, size);
    }

    @GetMapping(value = "/action/{userId}/sendRequest")
    public ResponseEntity<Friend> sendRequest(@PathVariable Long userId) {
        return friendRestService.sendRequest(userId);
    }

    @GetMapping(value = "/action/{userId}/removeRequest")
    public ResponseEntity<Friend> removeRequest(@PathVariable Long userId) {
        return friendRestService.removeRequest(userId);
    }

    @GetMapping(value = "/action/{userId}/removeFriend")
    public ResponseEntity<Friend> removeFriend(@PathVariable Long userId) {
        return friendRestService.removeFriend(userId);
    }

    @GetMapping(value = "/action/{userId}/acceptRequest")
    public ResponseEntity<Friend> acceptRequest(@PathVariable Long userId) {
        return friendRestService.acceptRequest(userId);
    }

}
