package evolution.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import evolution.dao.MessageDataServiceDeprecated;
import evolution.dao.UserDaoService;
import evolution.model.dialog.Dialog;
import evolution.model.message.Message;
import evolution.security.model.CustomSecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19.04.2017.
 */

@Controller
@RequestMapping("/im")
@SessionAttributes({"sel"})
public class MessageController {

    @Autowired
    private MessageDataServiceDeprecated messageDataServiceDeprecated;
    @Autowired
    private UserDaoService userDaoServiceImpl;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String im(
            @AuthenticationPrincipal CustomSecurityUser customUser,
            Model model,
            SessionStatus sessionStatus) {

        sessionStatus.setComplete();
        List<Message> list = messageDataServiceDeprecated.findLastMessageForDialog(customUser.getUser().getId());

        list.forEach(System.out::println);

        model.addAttribute("list", list);
        return "message/form-dialog";
    }

    @RequestMapping(value = "/{sel}", method = RequestMethod.GET)
    public String dialog(@AuthenticationPrincipal CustomSecurityUser  customUser,
                         @PathVariable Long sel,
                         Model model) {

        List<Message> list = messageDataServiceDeprecated.findMessage(customUser.getUser().getId(), sel, new PageRequest(0, 7));
        model.addAttribute("list", list);
        model.addAttribute("sel", sel);
        model.addAttribute("im", userDaoServiceImpl.selectIdFirstLastNameStandardUser(sel));
        return "message/form-message";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveMessage(
            @RequestParam String message,
            @RequestParam Long sel,
            @AuthenticationPrincipal CustomSecurityUser customUser,
            HttpServletResponse response) throws IOException {

        Dialog dialog = messageDataServiceDeprecated.selectDialogIdByFirstAndSecond(customUser.getUser().getId(), sel);
        if (dialog != null) {
            LOGGER.info("Dialog exist. Run save message");
            messageDataServiceDeprecated.save(new Message(customUser.getUser().getId(), message, new Date(), dialog.getId()));
        } else {
            LOGGER.info("create new dialog");
            messageDataServiceDeprecated.saveDialogAndMessage(customUser.getUser().getId(), sel, message, new Date());
            response.sendRedirect("/im/" + sel);
        }
    }

    @ResponseBody
    @GetMapping(value = "/getMessage",
            produces={"application/json; charset=UTF-8"})
    public List<Message> getMessage(
            @AuthenticationPrincipal CustomSecurityUser customUser,
            @RequestParam Long sel) throws JsonProcessingException {
        LOGGER.info("interval message");
        return messageDataServiceDeprecated.findMessageIgnoreDialog(customUser.getUser().getId(), sel, new PageRequest(0, 7));
    }
}
