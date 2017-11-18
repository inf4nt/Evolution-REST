package evolution.controller;


import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
import evolution.rest.api.FriendRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<FriendDTO>> findAll(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size) {
        return friendRestService.findAll(page, size);
    }

    @GetMapping(value = "/find/followers/{userId}")
    public ResponseEntity<Page<FriendDTO>> findUserFollowers(@PathVariable Long userId,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) {
        return friendRestService.findUserFollower(userId, page, size);
    }


    @GetMapping(value = "/find/requests/{userId}")
    public ResponseEntity<Page<FriendDTO>> findUserRequests(@PathVariable Long userId,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size) {
        return friendRestService.findUserRequest(userId, page, size);
    }


    @GetMapping(value = "/find/progress/{userId}")
    public ResponseEntity<Page<FriendDTO>> findUserProgress(@PathVariable Long userId,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size) {

        return friendRestService.findUserProgress(userId, page, size);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<FriendDTOFull>> findAllList() {
        return friendRestService.findAll2();
    }

    @GetMapping(value = "/find/followers/{userId}/list")
    public ResponseEntity<Page<FriendDTOFull>> findUserFollowers2(@PathVariable Long userId,
                                                                  @RequestParam(required = false) Integer page,
                                                                  @RequestParam(required = false) Integer size) {
        return friendRestService.findUserFollower2(userId, page, size);
    }

    @GetMapping(value = "/find/requests/{userId}/list")
    public ResponseEntity<Page<FriendDTOFull>> findUserRequests2(@PathVariable Long userId,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size) {
        return friendRestService.findUserRequest2(userId, page, size);
    }

    @GetMapping(value = "/find/progress/{userId}/list")
    public ResponseEntity<Page<FriendDTOFull>> findUserProgress2(@PathVariable Long userId,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size) {

        return friendRestService.findUserProgress2(userId, page, size);
    }

    @PostMapping(value = "/action/sendRequest")
    public ResponseEntity<FriendDTOFull> sendRequest(@RequestBody FriendActionDTO friendActionDTO) {
        return friendRestService.sendRequest(friendActionDTO);
    }

    @DeleteMapping(value = "/action/removeRequest")
    public ResponseEntity<FriendDTOFull> removeRequest(@RequestBody FriendActionDTO friendActionDTO) {
        return friendRestService.removeRequest(friendActionDTO);
    }

    @DeleteMapping(value = "/action/removeFriend")
    public ResponseEntity<FriendDTOFull> removeFriend(@RequestBody FriendActionDTO friendActionDTO) {
        return friendRestService.removeFriend(friendActionDTO);
    }

    @PutMapping(value = "/action/acceptRequest")
    public ResponseEntity<FriendDTOFull> acceptRequest(@RequestBody FriendActionDTO friendActionDTO) {
        return friendRestService.acceptRequest(friendActionDTO);
    }

}
