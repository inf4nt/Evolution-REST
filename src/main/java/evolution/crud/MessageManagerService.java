package evolution.crud;

import evolution.dto.MessageDTO;
import evolution.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface MessageManagerService {

    MessageDTO createNewMessage(Long iam, MessageDTO messageDTO);

    Optional<MessageDTO> findOne(Long id);

    Optional<MessageDTO> findOne(Long messageId, Long senderId);

    void deleteMessage(Long messageId, Long senderId);

    void deleteMessage(Long messageId);

    void deleteMessageAndMaybeDialog(Long messageId, Long senderId);

    void deleteMessageAndMaybeDialog(Long messageId);

    Page<MessageDTO> findLastMessageInMyDialogs(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<MessageDTO> findAllMessage(String sort, List<String> sortProperties);

    Page<MessageDTO> findAllMessage(Integer page, Integer size, String sort, List<String> sortProperties);

    Page<MessageDTO> findMessageByDialog(Long user1, Long user2, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<MessageDTO>findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);
}
