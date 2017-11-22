package evolution.controller;

import evolution.model.Feed;
import evolution.repository.FeedRepository;
import evolution.repository.FriendRepository;
import evolution.security.model.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/feed")
@CrossOrigin
public class FeedRestController {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FriendRepository friendRepository;

    @GetMapping(value = "/list")
    public List<Feed> findAll(@AuthenticationPrincipal CustomSecurityUser auth) {
        return feedRepository.findMyFriendsFeed(auth.getUser().getId());
    }

    @GetMapping(value = "/page")
    public Page<Feed> findAll2(@AuthenticationPrincipal CustomSecurityUser auth) {
        return feedRepository.findMyFriendsFeed(auth.getUser().getId(), new PageRequest(0, 100));
//        return null;
    }

}
