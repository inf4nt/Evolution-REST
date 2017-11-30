package evolution.rest.api;

import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
import evolution.dto.model.MessageForUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
public interface MessageRestService extends AbstractRestService {

    ResponseEntity<List<MessageDTO>> findAll();

    ResponseEntity<Page<MessageDTO>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageRecipientId(Long recipient, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageSenderId(Long sender, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(Long userId);

    ResponseEntity<MessageDTO> findOneMessage(Long id);

    ResponseEntity<MessageDTO> save(MessageDTOForSave message);

    ResponseEntity<HttpStatus> update(MessageForUpdateDTO message);

    ResponseEntity<MessageForUpdateDTO> updateAfterReturn(MessageForUpdateDTO message);

    ResponseEntity<HttpStatus> updateMessage(MessageForUpdateDTO message);

    ResponseEntity<HttpStatus> delete(Long messageId);

    ResponseEntity<Long> deleteAfterReturnId(Long messageId);

    ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId);

    ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(Long interlocutor);

    ResponseEntity<Page<MessageDTO>> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties);
}
