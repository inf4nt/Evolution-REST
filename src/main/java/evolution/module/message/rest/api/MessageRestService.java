package evolution.module.message.rest.api;


import evolution.message.dto.MessageSaveDTO;
import evolution.message.dto.MessageUpdateDTO;
import evolution.module.message.dto.MessageDTO;
import evolution.rest.api.AbstractRestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
public interface MessageRestService extends AbstractRestService {

    ResponseEntity<List<MessageDTO>> findAll();

    ResponseEntity<List<MessageDTO>> findAll(String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageRecipientId(Long recipient);

    ResponseEntity<List<MessageDTO>> findMessageRecipientId(Long recipient, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageRecipientId(Long recipient, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageSenderId(Long senderId);

    ResponseEntity<List<MessageDTO>> findMessageSenderId(Long sender, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageSenderId(Long sender, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(Long userId);

    ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(Long userId, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<MessageDTO> findOne(Long id);

    ResponseEntity<MessageDTO> save(MessageSaveDTO message);

    ResponseEntity<HttpStatus> update2(MessageUpdateDTO message);

    ResponseEntity<MessageDTO> update(MessageUpdateDTO message);

    ResponseEntity<HttpStatus> delete(Long messageId);

    ResponseEntity<HttpStatus> delete(List<Long> list);

    ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(Long interlocutor);

    ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(Long interlocutor, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties);
}
