package evolution.crud.old;

import evolution.dto.MessageDTO;
import evolution.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface MessageCrudManagerService {

    MessageDTO createNewMessage(Long iam, MessageDTO messageDTO);

    Optional<Message> findOne(Long id);

    Optional<Message> findOne(Long messageId, Long senderId);

    void deleteMessage(Long messageId, Long senderId);

    void deleteMessage(Long messageId);

    void deleteMessageAndMaybeDialog(Long messageId, Long senderId);

    void deleteMessageAndMaybeDialog(Long messageId);

    Page<Message> findLastMessageInMyDialogs(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Message> findAllMessage(String sort, List<String> sortProperties);

    Page<Message> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    Page<Message> findMessageByDialog(Long user1, Long user2, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<Message>findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);
}
