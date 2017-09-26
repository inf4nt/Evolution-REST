package evolution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import evolution.common.FriendActionEnum;
import evolution.common.FriendStatusEnum;
import evolution.dao.FriendsDao;
import evolution.dao.UserDaoService;
import evolution.model.friend.Friends;
import evolution.model.jsonModel.JsonInformation;
import evolution.security.model.CustomSecurityUser;
import evolution.service.MyJacksonService;
import evolution.service.builder.JsonInformationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 21.05.2017.
 */
@Controller
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendsDao friendsDao;
    @Autowired
    private JsonInformationBuilder jsonBuilder;
    @Autowired
    private MyJacksonService jacksonService;

    @Autowired
    private UserDaoService userDaoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendController.class);
    private static String responseJson;

    @GetMapping(value = "/{userId}")
    public String friends(@PathVariable Long userId,
                          @AuthenticationPrincipal CustomSecurityUser customUser,
                          Model model,
                          HttpServletRequest request){

        int limit = 10;
        model.addAttribute("limit", limit);

        Map<String, List<Friends>> map;
        if (request.isUserInRole("ROLE_ADMIN") || customUser.getUser().getId().equals(userId)) {
            map = friendsDao.friend(userId, limit, 0);
            model.addAttribute(FriendStatusEnum.REQUEST.toString().toLowerCase(),
                    map.get(FriendStatusEnum.REQUEST.toString().toLowerCase()));
        } else {
            map = friendsDao.friendFollower(userId, limit, 0);
        }
        model.addAttribute(FriendStatusEnum.PROGRESS.toString().toLowerCase(),
                map.get(FriendStatusEnum.PROGRESS.toString().toLowerCase()
        ));
        model.addAttribute(FriendStatusEnum.FOLLOWER.toString().toLowerCase(),
                map.get(FriendStatusEnum.FOLLOWER.toString().toLowerCase()));

        if (!map.get(FriendStatusEnum.PROGRESS.toString().toLowerCase()).isEmpty())
            model.addAttribute("user", map.get(FriendStatusEnum.PROGRESS.toString().toLowerCase()).get(0).getUser());
        else if (!map.get(FriendStatusEnum.FOLLOWER.toString().toLowerCase()).isEmpty())
            model.addAttribute("user", map.get(FriendStatusEnum.FOLLOWER.toString().toLowerCase()).get(0).getUser());
        else if (!map.get(FriendStatusEnum.REQUEST.toString().toLowerCase()).isEmpty())
            model.addAttribute("user", map.get(FriendStatusEnum.REQUEST.toString().toLowerCase()).get(0).getUser());
        else
            model.addAttribute("user", userDaoService.selectIdFirstLastNameStandardUser(userId));
        return "user/form-friend";
    }


    @ResponseBody
    @GetMapping(value = "/{status}/{userId}",
            produces={"application/json; charset=UTF-8"})
    public List moreFriend(@PathVariable Long userId,
                         @PathVariable String status,
                         @RequestParam Integer limit,
                         @RequestParam Integer offset,
                         HttpServletRequest request,
                         @AuthenticationPrincipal CustomSecurityUser customUser) throws JsonProcessingException {

        List list = new ArrayList();
        if (FriendStatusEnum.PROGRESS.toString().equalsIgnoreCase(status)) {
            list = friendsDao.moreFriend(userId, limit, offset);
        }
        else if (FriendStatusEnum.FOLLOWER.toString().equalsIgnoreCase(status)) {
            list =  friendsDao.moreFollower(userId, limit, offset);
        }
        else if ((request.isUserInRole("ROLE_ADMIN") || customUser.getUser().getId().equals(userId))
                && FriendStatusEnum.REQUEST.toString().equalsIgnoreCase(status)){
            list =  friendsDao.moreRequest(userId, limit, offset);
        }
        return list;
    }


    @ResponseBody
    @PutMapping(value = "/",
            produces={"application/json; charset=UTF-8"})
    public String friendAction (@RequestBody String json,
                                @AuthenticationPrincipal CustomSecurityUser customUser) throws IOException {

        JsonInformation jsonInformation = (JsonInformation) jacksonService.jsonToObject(json, JsonInformation.class);
        LOGGER.info("PARSE JSON SUCCESS \n" + jsonInformation + "\n");

        Long friendId = Long.parseLong(jsonInformation.getInfo().toString());
        Long userId = customUser.getUser().getId();

        LOGGER.info("START " + jsonInformation.getMessage() + "\n" + "authUserId = " +userId + ", friendId = " + friendId);
        if (jsonInformation.getMessage().equals(FriendActionEnum.DELETE_FRIEND.toString())) {

            friendsDao.deleteFriend(userId, friendId);
            responseJson = jsonBuilder.buildJson(HttpStatus.OK.toString(), FriendActionEnum.ACCEPT_REQUEST.toString(), true);

        } else if (jsonInformation.getMessage().equals(FriendActionEnum.ACCEPT_REQUEST.toString())) {
            friendsDao.acceptFriend(userId, friendId);
            responseJson = jsonBuilder.buildJson(HttpStatus.OK.toString(), FriendActionEnum.DELETE_FRIEND.toString(), true);

        } else if (jsonInformation.getMessage().equals(FriendActionEnum.REQUEST_FRIEND.toString()) &&
                !friendsDao.checkFriends(userId, friendId)) {
            friendsDao.friendRequest(userId, friendId);
            responseJson = jsonBuilder.buildJson(HttpStatus.OK.toString(), FriendActionEnum.DELETE_REQUEST.toString(), true);

        } else if (jsonInformation.getMessage().equals(FriendActionEnum.DELETE_REQUEST.toString())) {
            friendsDao.deleteRequest(userId, friendId);
            responseJson = jsonBuilder.buildJson(HttpStatus.OK.toString(), FriendActionEnum.REQUEST_FRIEND.toString(), true);
        }

        LOGGER.info("RESPONSE JSON \n" + responseJson + "\n");
        return responseJson;
    }


}
