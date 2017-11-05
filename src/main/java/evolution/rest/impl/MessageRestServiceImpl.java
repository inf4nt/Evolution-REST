package evolution.rest.impl;

import evolution.helper.HelperDataService;
import evolution.data.MessageDataService;
import evolution.helper.HelperRestService;
import evolution.model.Message;
import evolution.model.User;
import evolution.rest.api.MessageRestService;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    public MessageRestServiceImpl(SecuritySupportService securitySupportService,
                                  MessageDataService messageDataService,
                                  HelperDataService helperDataService, HelperRestService<Message> helperRestService) {
        this.securitySupportService = securitySupportService;
        this.messageDataService = messageDataService;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
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
    public ResponseEntity<Page<Message>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);

        return serviceResultAndRepair(messageDataService.findMessageByDialog(dialogId, pageable));
    }

    @Override
    public ResponseEntity<Page<Message>> findMessageByAuthUser(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();

        return serviceResultAndRepair(messageDataService.findMessageByUser(auth.getId(), pageable));
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
