package evolution.controller;


import evolution.dto.model.FriendDTO;
import evolution.rest.api.FriendRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<FriendDTO>> findAll(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size) {
        return friendRestService.findAll(page, size);
    }

    @GetMapping(value = "/{userId}/status/follower")
    public ResponseEntity<Page<FriendDTO>> findUserFollowers(@PathVariable Long userId,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) {
        return friendRestService.findUserFollower(userId, page, size);
    }


    @GetMapping(value = "/{userId}/status/request")
    public ResponseEntity<Page<FriendDTO>> findUserRequests(@PathVariable Long userId,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {
        return friendRestService.findUserRequest(userId, page, size);
    }


    @GetMapping(value = "/{userId}/status/progress")
    public ResponseEntity<Page<FriendDTO>> findUserProgress(@PathVariable Long userId,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {

        return friendRestService.findUserProgress(userId, page, size);
    }

    @GetMapping(value = "/action/{userId}/sendRequest")
    public ResponseEntity<FriendDTO> sendRequest(@PathVariable Long userId) {
        return friendRestService.sendRequest(userId);
    }

    @GetMapping(value = "/action/{userId}/removeRequest")
    public ResponseEntity<FriendDTO> removeRequest(@PathVariable Long userId) {
        return friendRestService.removeRequest(userId);
    }

    @GetMapping(value = "/action/{userId}/removeFriend")
    public ResponseEntity<FriendDTO> removeFriend(@PathVariable Long userId) {
        return friendRestService.removeFriend(userId);
    }

    @GetMapping(value = "/action/{userId}/acceptRequest")
    public ResponseEntity<FriendDTO> acceptRequest(@PathVariable Long userId) {
        return friendRestService.acceptRequest(userId);
    }

}
