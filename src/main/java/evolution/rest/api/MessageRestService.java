package evolution.rest.api;

import evolution.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
public interface MessageRestService {

    ResponseEntity<Page<Message>> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<Message>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

//    ResponseEntity<Page<Dialog>> findAllDialogByAuthUser(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<Message>> findMessageByAuthUser(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<Message>> findLastMessageInDialogByAuthUser(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<Message>> findLastMessageInDialog(Long userId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Message> findOneMessage(Long id);

    ResponseEntity<Message> findOneMessageByAuthSender(Long id);

//    ResponseEntity<Page<Dialog>> findAllDialog(Integer page, Integer size, String sort, List<String> sortProperties);

//    ResponseEntity<Dialog> findOneDialog(Long id);

//    ResponseEntity<Dialog> findOneDialogByUserAndCheckAccess(Long id);

//    ResponseEntity<HttpStatus> save(Message message);

//    ResponseEntity<Message> saveMessage(Message message);

    ResponseEntity<HttpStatus> update(Message message);

    ResponseEntity<Message> updateAfterReturn(Message message);

    ResponseEntity<HttpStatus> updateMessage(Long messageId, String message);

    ResponseEntity<HttpStatus> updateMessageByAuthUser(Long messageId, String message);
//    ResponseEntity<HttpStatus> deleteMessage(Long messageId);

//    ResponseEntity<Long> deleteAndReturn(Long id);

//    ResponseEntity<HttpStatus> deactivated(Long id);
}
