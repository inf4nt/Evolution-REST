package evolution.controller;


import evolution.data.MessageDataService;
import evolution.model.message.Message;
import evolution.security.model.CustomSecurityUser;
import evolution.service.MyJacksonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 02.09.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/message")
public class RestMessageController {

    private final MessageDataService messageDataService;

    @Autowired
    public RestMessageController(MessageDataService messageDataService) {
        this.messageDataService = messageDataService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional optional = this.messageDataService.findOne(id);
        System.out.println(optional);
        if (!optional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping
    public ResponseEntity findAllMessage() {
        List<Message> messages = this.messageDataService.findAll();
        if (messages.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping(value = "/last_from_my_dialog")
    public ResponseEntity findLastMessageForDialog(@AuthenticationPrincipal CustomSecurityUser customSecurityUser) {
        List<Message> dialogList = this.messageDataService.findLastMessageForDialog(customSecurityUser.getUser().getId());
        if (dialogList.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(dialogList, HttpStatus.OK);
    }

    @GetMapping(value = "/last_from_my_dialog/repair_dialog")
    public ResponseEntity findLastMessageForDialogRepairDialog(@AuthenticationPrincipal CustomSecurityUser customSecurityUser) {
        List<Message> dialogList = this.messageDataService.findLastMessageForDialogAfterRepairDialog(customSecurityUser.getUser().getId());
        if (dialogList.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(dialogList, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity findMessageByUserId(@PathVariable Long id,
                                              @AuthenticationPrincipal CustomSecurityUser customSecurityUser) {
        List<Message> list = messageDataService.findMessageByUsers(customSecurityUser.getUser().getId(), id, new PageRequest(0, 7));
        if (list.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/repair_dialog")
    public ResponseEntity findMessageByUserIdRepairDialog(@PathVariable Long id,
                                                          @AuthenticationPrincipal CustomSecurityUser customSecurityUser) {
        List<Message> list = messageDataService.findMessageByUsersRepairDialog(customSecurityUser.getUser().getId(), id, new PageRequest(0, 7));
        if (list.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postMessage(@RequestBody Message message) {
        try {
            Message result = messageDataService.save(message);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMessageByIdAdmin(@PathVariable Long id) {
        try {
            messageDataService.deleteByAuthentication(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/delete/{id}/admin")
    public ResponseEntity deleteMessageByIdGET(@PathVariable Long id) {
        try {
            messageDataService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}/admin")
    public ResponseEntity deleteMessageById(@PathVariable Long id) {
        try {
            messageDataService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
