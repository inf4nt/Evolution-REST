package evolution.rest;


import com.sun.org.apache.regexp.internal.RE;
import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.MessageBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.MessageDTO;

import evolution.dto.model.MessageDTOForSave;
import evolution.rest.api.MessageRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
@Service
public class MessageRestServiceImpl implements MessageRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageBusinessService messageBusinessService;

    @Autowired
    public MessageRestServiceImpl(MessageBusinessService messageBusinessService) {
        this.messageBusinessService = messageBusinessService;
    }


    @Override
    public ResponseEntity<List<MessageDTO>> findAll() {
        List<MessageDTO> list = messageBusinessService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findAll(page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(p);
        }
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByAuthUserAndRecipientId(Long interlocutorId, Integer page, Integer size, String sort, List<String> sortProperties) {
//        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//
//        Page<Message> p = messageDataService.findMessageByDialogUsers(auth.getId(), interlocutorId, pageable);
//
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Integer page, Integer size, String sort, List<String> sortProperties) {
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//        return findLastMessageInMyDialog(auth.getId(), page, size, sort, sortProperties);
        return null;
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties) {
//        Pageable pageable = helperDataService.getPageableForMessage(page, size, sort, sortProperties);
//
//        return serviceResultAndRepair(messageDataService.findLastUserMessageInDialogWhereUserId(userId, pageable));
        return null;
    }

    @Override
    public ResponseEntity<MessageDTO> findOneMessage(Long id) {
//        Optional<Message> optional = messageDataService.findOne(id);
//        if (!optional.isPresent()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//
//        Message message = helperDataService.repairDialog(optional.get(), auth);
//
//        return ResponseEntity.ok(message);
        return null;
    }

    @Override
    public ResponseEntity<MessageDTO> findOneByMessageIdAndSenderId(Long id) {
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//
//        Optional<Message> optional = messageDataService.findOne(id, auth.getId());
//        if (!optional.isPresent()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        Message message = helperDataService.repairDialog(optional.get(), auth);
//
//        return ResponseEntity.ok(message);
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> save(MessageDTOForSave message) {
        BusinessServiceExecuteResult b = messageBusinessService.createMessage(message.getSenderId(), message.getRecipientId(), message.getText());
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<MessageDTO> saveMessage(MessageDTO message) {
//        try {
//            DateTime dateTime = DateTime.now(DateTimeZone.UTC);
//            message.setDateDispatch(new Date(dateTime.getMillis()));
//            //todo validate message
//            Message m = messageDataService.save(message);
//            return ResponseEntity.ok(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(500).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> update(MessageDTO message) {
//        try {
//            messageDataService.save(message);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<MessageDTO> updateAfterReturn(MessageDTO message) {
//        try {
//            Message m = messageDataService.save(message);
//            return ResponseEntity.ok(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> updateMessage(MessageDTO message) {
//        Optional<Message> optional = messageDataService.findOne(messageId);
//        if (optional.isPresent()) {
//            Message m = optional.get();
//            m.setMessage(message);
//            messageDataService.save(m);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> updateMessageByAuthUser(MessageDTO messageDTO) {
//        Optional<Message> optional = messageDataService.findOne(messageId);
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//        if (optional.isPresent()) {
//            Message m = optional.get();
//
//            // check sender
//            if (!m.getSender().getId().equals(auth.getId())) {
//                return ResponseEntity.status(417).build();
//            }
//
//            m.setMessage(message);
//            messageDataService.save(m);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(417).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long messageId) {
        return null;
    }

    @Override
    public ResponseEntity<Long> deleteAfterReturnId(Long messageId) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteByMessageIdAndSenderId(Long messageId) {
        return null;
    }

    @Override
    public ResponseEntity<Long> deleteByMessageIdAndSenderIdAfterReturnId(Long messageId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findMessageByDialogId(dialogId, page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(p);
        }
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId, String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId) {
        List<MessageDTO> list = messageBusinessService.findMessageByDialogId(dialogId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findMessageByDialogIdAndUserIam(dialogId, page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findMessageByDialogIdAndUserIam(dialogId, sort, sortProperties);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId) {
        List<MessageDTO> list = messageBusinessService.findMessageByDialogIdAndUserIam(dialogId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
