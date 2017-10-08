package evolution.controller;

import evolution.dao.FeedServiceDao;
import evolution.model.user.UserLight;
import evolution.model.user.StockUser;
import evolution.security.model.CustomSecurityUser;
import evolution.service.MyJacksonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Infant on 26.07.2017.
 */
@Controller
@RequestMapping(value = "/feed")
public class FeedController {

    @Autowired
    private FeedServiceDao feedServiceDao;

    @Autowired
    private MyJacksonService jacksonService;

    @ResponseBody
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Feed> allFeeds() {
        return feedServiceDao.findAll();
    }

    @GetMapping(value = "/user/{user_id}/get/view")
    public ModelAndView getNewsView(@PathVariable(value = "user_id") Long userId) {
        ModelAndView modelAndView = new ModelAndView("feed/feed-friends");
        modelAndView.addObject("list", feedServiceDao.findFeedsOfMyFriends(userId));
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "/user/{user_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getNewsByUserId(@PathVariable(value = "user_id") Long userId) {
        return feedServiceDao.findFeedsOfMyFriends(userId);
    }

    @PostMapping(value = "/post/view")
    public String postFeed(@RequestParam(name = "tweet-content") String tweetContent,
                           @AuthenticationPrincipal CustomSecurityUser customUser,
                           @SessionAttribute StockUser user) throws IOException {
        Feed feed = new Feed();
        feed.setContent(tweetContent);
        feed.setSender(new UserLight(customUser.getUser().getId()));
        feed.setDate(new Date());

        if (!user.getId().equals(customUser.getUser().getId())) {
            feed.setToUser(new UserLight(user.getId(), user.getFirstName(), user.getFirstName()));
        }

        if (!feed.getContent().isEmpty()) {
            feedServiceDao.save(feed);
        }
        return "redirect:/user/id" + user.getId();
    }

    @GetMapping(value = "/{id}/delete/view")
    public String deleteFeed(@PathVariable(value = "id") Long feedId,
                             @AuthenticationPrincipal CustomSecurityUser customUser,
                             @SessionAttribute StockUser user) {
        feedServiceDao.delete(feedId, customUser.getUser().getId());
        return "redirect:/user/id" + user.getId();
    }

    @GetMapping(value = "/feed-message/{id}/delete/view")
    public String deleteFeedMessage(@PathVariable(value = "id") Long feedId,
                                    @AuthenticationPrincipal CustomSecurityUser customUser,
                                    @SessionAttribute StockUser user) {
        feedServiceDao.deleteFeedMessage(feedId, customUser.getUser().getId());
        return "redirect:/user/id" + user.getId();
    }
}
