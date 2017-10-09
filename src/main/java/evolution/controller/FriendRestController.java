package evolution.controller;

import evolution.common.FriendStatusEnum;
import evolution.data.FriendRepository;
import evolution.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Infant on 09.10.2017.
 */
@RestController
@RequestMapping(value = "/friend")
@CrossOrigin
public class FriendRestController {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendRestController(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @GetMapping(value = "/{id}/my_follower")
    public Friend findFollowerByUser(@PathVariable Long id) {
        return friendRepository.findFollowerByUser(id, FriendStatusEnum.REQUEST);
    }

    @GetMapping(value = "/{id}/my_request")
    public Friend findRequestFromUser(@PathVariable Long id) {
        return friendRepository.findRequestFromUser(id, FriendStatusEnum.REQUEST);
    }

    @GetMapping(value = "/{id}/my_progress")
    public Friend finProgressByUser(@PathVariable Long id) {
        return friendRepository.findProgressByUser(id, FriendStatusEnum.PROGRESS);
    }

}
