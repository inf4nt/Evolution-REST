package evolution.rest.impl;

import evolution.data.DialogDataService;
import evolution.data.UserDataService;
import evolution.helper.HelperDataService;
import evolution.data.MessageDataService;
import evolution.helper.HelperRestService;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.rest.api.DialogRestService;
import evolution.rest.api.MessageRestService;
import evolution.service.SecuritySupportService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 28.10.2017.
 */
@Service
public class MessageRestServiceImpl implements MessageRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SecuritySupportService securitySupportService;

    private final MessageDataService messageDataService;

    private final HelperDataService helperDataService;

    private final HelperRestService<Message> helperRestService;

    private final DialogDataService dialogDataService;

    private final UserDataService userDataService;

    @Autowired
    public MessageRestServiceImpl(SecuritySupportService securitySupportService,
                                  MessageDataService messageDataService,
                                  HelperDataService helperDataService,
                                  HelperRestService<Message> helperRestService, DialogDataService dialogDataService, UserDataService userDataService) {
        this.securitySupportService = securitySupportService;
        this.messageDataService = messageDataService;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
        this.dialogDataService = dialogDataService;
        this.userDataService = userDataService;
    }

    private ResponseEntity<Page<Message>> serviceResultAndRepair(Page<Message> page) {
        Page<Message> result = helperDataService.repairPageMessage(page);

        return helperRestService.getResponseForPage(result);
    }

    @Override
    public ResponseEntity<Page<Message>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);

        return serviceResultAndRepair(messageDataService.findAll(pageable));
    }

    @Override
    public ResponseEntity<Page<Message>> findMessageByAuthUserAndInterlocutor(Long interlocutorId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();

        Page<Message> p = messageDataService.findMessageByDialogUsers(auth.getId(), interlocutorId, pageable);

        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<Page<Message>> findLastMessageInDialogByAuthUser(Integer page, Integer size, String sort, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return findLastMessageInDialog(auth.getId(), page, size, sort, sortProperties);
    }

    @Override
    public ResponseEntity<Page<Message>> findLastMessageInDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);

        return serviceResultAndRepair(messageDataService.findLastUserMessageInDialogWhereUserId(userId, pageable));
    }

    @Override
    public ResponseEntity<Message> findOneMessage(Long id) {
        Optional<Message> optional = messageDataService.findOne(id);
        if (!optional.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        User auth = securitySupportService.getAuthenticationPrincipal().getUser();

        Message message = helperDataService.repairDialog(optional.get(), auth);

        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<Message> findOneMessageByAuthSender(Long id) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();

        Optional<Message> optional = messageDataService.findOne(id, auth.getId());
        if (!optional.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        Message message = helperDataService.repairDialog(optional.get(), auth);

        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<HttpStatus> save(Message message) {
        try {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            Long secondId = message.getDialog().getSecond().getId();

            Optional<Dialog> optional = dialogDataService.findDialogWhereUsers(auth.getId(), secondId);
            List<Message> messageList;

            DateTime dateTime = DateTime.now(DateTimeZone.UTC);
            Dialog dialog;
            if (optional.isPresent()) {
                // dialog exist
                dialog = optional.get();
                messageList = dialog.getMessageList();
            } else {
                // create new dialog
                dialog = new Dialog();
                dialog.setCreateDate(new Date(dateTime.getMillis()));
                dialog.setFirst(auth);
                Optional<User> second = userDataService.findOne(secondId);
                if(!second.isPresent()) {
                    logger.info("second user in dialog not found. Second user id " + secondId);
                    return ResponseEntity.status(417).build();
                }
                dialog.setSecond(second.get());
                messageList = new ArrayList<>();
                dialog.setMessageList(messageList);
            }

            message.setDateDispatch(new Date(dateTime.getMillis()));
            message.setSender(auth);
            message.setDialog(dialog);

            messageList.add(message);

            dialogDataService.save(dialog);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Message> saveMessage(Message message) {
        try {
            DateTime dateTime = DateTime.now(DateTimeZone.UTC);
            message.setDateDispatch(new Date(dateTime.getMillis()));
            //todo validate message
            Message m = messageDataService.save(message);
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> update(Message message) {
        try {
            messageDataService.save(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<Message> updateAfterReturn(Message message) {
        try {
            Message m = messageDataService.save(message);
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> updateMessage(Long messageId, String message) {
        Optional<Message> optional = messageDataService.findOne(messageId);
        if (optional.isPresent()) {
            Message m = optional.get();
            m.setMessage(message);
            messageDataService.save(m);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> updateMessageByAuthUser(Long messageId, String message) {
        Optional<Message> optional = messageDataService.findOne(messageId);
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        if (optional.isPresent()) {
            Message m = optional.get();

            // check sender
            if (!m.getSender().getId().equals(auth.getId())) {
                return ResponseEntity.status(417).build();
            }

            m.setMessage(message);
            messageDataService.save(m);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }
}
