package evolution.rest.api;

import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
public interface MessageRestService {

    ResponseEntity<List<MessageDTO>> findAll();

    ResponseEntity<Page<MessageDTO>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageByAuthUserAndRecipientId(Long interlocutorId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<MessageDTO> findOneMessage(Long id);

    ResponseEntity<MessageDTO> findOneByMessageIdAndSenderId(Long id);

    ResponseEntity<HttpStatus> save(MessageDTOForSave message);

    ResponseEntity<MessageDTO> saveMessage(MessageDTO message);

    ResponseEntity<HttpStatus> update(MessageDTO message);

    ResponseEntity<MessageDTO> updateAfterReturn(MessageDTO message);

    ResponseEntity<HttpStatus> updateMessage(MessageDTO message);

    ResponseEntity<HttpStatus> updateMessageByAuthUser(MessageDTO message);

    ResponseEntity<HttpStatus> delete(Long messageId);

    ResponseEntity<Long> deleteAfterReturnId(Long messageId);

    ResponseEntity<HttpStatus> deleteByMessageIdAndSenderId(Long messageId);

    ResponseEntity<Long> deleteByMessageIdAndSenderIdAfterReturnId(Long messageId);

    ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId);

    ResponseEntity<Page<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId);
}
