package evolution.rest;



import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.MessageBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import evolution.rest.api.MessageRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private final MessageBusinessService messageBusinessService;

    @Autowired
    public MessageRestServiceImpl(MessageBusinessService messageBusinessService) {
        this.messageBusinessService = messageBusinessService;
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findAll() {
        List<MessageDTO> list = messageBusinessService.findAll();
        return response(list);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findAll(String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findAll(sort, sortProperties);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findAll(page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageRecipientId(Long recipient) {
        List<MessageDTO> list = messageBusinessService.findMessageByRecipientId(recipient);
        return response(list);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageRecipientId(Long recipient, String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findMessageByRecipientId(recipient, sort, sortProperties);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageRecipientId(Long recipient, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findMessageByRecipientId(recipient, page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageSenderId(Long senderId) {
        List<MessageDTO> list = messageBusinessService.findMessageBySenderId(senderId);
        return response(list);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageSenderId(Long sender, String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findMessageBySenderId(sender, sort, sortProperties);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageSenderId(Long sender, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findMessageBySenderId(sender, page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(Long userId, String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findLastMessageInMyDialog(userId, sort, sortProperties);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findLastMessageInMyDialog(userId, page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(Long userId) {
        List<MessageDTO> list = messageBusinessService.findLastMessageInMyDialog(userId);
        return response(list);
    }

    @Override
    public ResponseEntity<MessageDTO> findOne(Long id) {
        Optional<MessageDTO> m = messageBusinessService.findOne(id);
        return response(m);
    }

    @Override
    public ResponseEntity<MessageDTO> save(MessageSaveDTO message) {
        BusinessServiceExecuteResult<MessageDTO> b = messageBusinessService.createMessage(message.getSenderId(), message.getRecipientId(), message.getText());
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.status(201).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HttpStatus> update2(MessageUpdateDTO message) {
        return ResponseEntity.status(update(message).getStatusCode()).build();
    }

    @Override
    public ResponseEntity<MessageDTO> update(MessageUpdateDTO message) {
        BusinessServiceExecuteResult<MessageDTO> b = messageBusinessService.update(message);

        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObject());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long messageId) {
        messageBusinessService.delete(messageId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(List<Long> list) {
        messageBusinessService.delete(list);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(Long interlocutor) {
        List<MessageDTO> list = messageBusinessService.findMessageByInterlocutor(interlocutor);
        return response(list);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(Long interlocutor, String sort, List<String> sortProperties) {
        List<MessageDTO> list = messageBusinessService.findMessageByInterlocutor(interlocutor, sort, sortProperties);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = messageBusinessService.findMessageByInterlocutor(interlocutor, page, size, sort, sortProperties);
        return response(p);
    }

}
