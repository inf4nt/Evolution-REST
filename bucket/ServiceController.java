package evolution.controller;

import evolution.common.UserRoleEnum;
import evolution.dao.UserDaoService;
import evolution.model.user.User;
import evolution.model.userToken.AbstractToken;
import evolution.model.userToken.UserToken;
import evolution.service.MyJacksonService;
import evolution.service.builder.JsonInformationBuilder;
import evolution.service.notification.NotificationUser;
import evolution.service.validation.Validator;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Admin on 10.05.2017.
 */

@Controller
@RequestMapping(value = "/service")
@SessionAttributes(value = {"entityToken"})
public class ServiceController {

    @Autowired
    private JsonInformationBuilder jsonInformationBuilder;
    @Autowired
    private NotificationUser notificationUser;
    @Autowired
    private Validator validator;
    @Autowired
    private MyJacksonService jacksonService;


    @Autowired
    private UserDaoService userDaoService;

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);

    @RequestMapping(value = "/user/forgot/{token}", method = RequestMethod.GET)
    public String userForgotPasswordToken(@PathVariable String token,
                                          @SessionAttribute(required = false) AbstractToken entityToken,
                                          SessionStatus sessionStatus) throws IOException {

        if (entityToken != null
                && token.equals(entityToken.getToken())
                && validator.userValidator(((UserToken)entityToken).getUser())) {

            String newPassword = RandomStringUtils.randomAlphanumeric(32);
            LOGGER.info("Generate new password = " + newPassword);
            LOGGER.info("update user");
            ((UserToken)entityToken).getUser().setPassword(newPassword);

//            userDaoService.createNewUser(((UserToken) entityToken).getUser());

            LOGGER.info("send notification");
            notificationUser.successUserForgot((UserToken) entityToken);
            sessionStatus.setComplete();
            return "redirect:/welcome?info=Restore password successful. A new password has been sent to your e-mail";
        }
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/user/registration/{token}", method = RequestMethod.GET)
    public String userRegistrationToken (@PathVariable String token,
                                         @SessionAttribute(required = false) AbstractToken entityToken,
                                         SessionStatus sessionStatus) throws IOException {

        if (entityToken != null
                && token.equals(entityToken.getToken())
                && validator.userValidator(((UserToken)entityToken).getUser())) {

            if (checkExistUser(((UserToken) entityToken).getUser().getUsername())) {
                return "redirect:/welcome?info=This user by username " +
                        ((UserToken) entityToken).getUser().getUsername()
                        + " already exist";
            }


            User user = ((UserToken)entityToken).getUser();
//            userDaoService.createNewUser(user);
            notificationUser.successUserRegistration((UserToken) entityToken);

            LOGGER.info("User is valid, success registration");
            sessionStatus.setComplete();
            return "redirect:/welcome?info=Registration success. Your e-mail has been sent registration information";
        }
        return "redirect:/welcome";
    }


    @ResponseBody
    @RequestMapping(value = "/user/forgot/CREATE_FORGOT_TOKEN", method = RequestMethod.POST,
            produces={"application/json; charset=UTF-8"})
    public String userCreateForgotToken(@RequestBody String json,
                                        Model model) throws IOException {
        User user = (User) jacksonService.jsonToObject(json, User.class);
        User u = userDaoService.findUserByUsername(user.getUsername());


        if (u == null)
            return jsonInformationBuilder.buildJson(HttpStatus.OK.toString(), "User " + user.getUsername()+", is not exist\n", false);


        AbstractToken entityToken = new UserToken(UUID.randomUUID().toString(), u);
        model.addAttribute("entityToken", entityToken);
        LOGGER.info("user-forgot\n entityToken = " + entityToken.toString());
        notificationUser.forgotPassword((UserToken) entityToken);
        return jsonInformationBuilder.buildJson(HttpStatus.OK.toString(),
                "Your e-mail has been sent further instructions",
                true);
    }

    @ResponseBody
    @RequestMapping(value = "/user/registration/CREATE_REGISTRATION_TOKEN", method = RequestMethod.POST,
            produces={"application/json; charset=UTF-8"})
    public String userCreateRegistrationToken(@RequestBody String json, Model model) throws IOException {

        User user = (User) jacksonService.jsonToObject(json, User.class);

        user.setRoleId(UserRoleEnum.USER.getId());
        user.setRegistrationDate(new Date());

        if (validator.userValidator(user)) {
            AbstractToken entityToken = new UserToken(UUID.randomUUID().toString(), user);
            model.addAttribute("entityToken", entityToken);
            LOGGER.info("Added to session attribute \n" + entityToken.toString() + "\n");
            notificationUser.sendTokenUserRegistration((UserToken) entityToken);
            return jsonInformationBuilder.buildJson(HttpStatus.OK.toString(),
                    "Your e-mail has been sent further instructions",
                    true);
        } else {
            return jsonInformationBuilder.buildJson(HttpStatus.OK.toString(),
                    "User is not valid",
                    false);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/is-exist", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean checkExistUser(@RequestParam String username) {
        User user = userDaoService.findUserByUsername(username);
        LOGGER.info("check user = " + user);
        return user != null;
    }
}
