package evolution.crud.api;

import evolution.dto.model.MessageDTO;
import evolution.model.Message;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface MessageCrudManagerService extends AbstractCrudManagerService<Message, Long>, PageableManager {

    Optional<Message> findOneByMessageIdAndSenderId(Long messageId, Long senderId);

    List<Message> findMessageByDialogId(Long dialogId);

    List<Message> findMessageByDialogId(Long dialogId, String sort, List<String> sortProperties);

    Page<Message> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<Message> findMessageByDialogId(Long dialogId, Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Message> findMessageByDialogId(Long dialogId, Long iam, String sort, List<String> sortProperties);

    List<Message> findMessageByDialogId(Long dialogId, Long iam);

    Page<Message> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Message> findLastMessageInMyDialog(Long iam);

    List<Message> findLastMessageInMyDialog(Long iam, String sort, List<String> sortProperties);

    Page<Message> findMessageBySenderId(Long senderId, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Message> findMessageBySenderId(Long senderId);

    List<Message> findMessageBySenderId(Long senderId, String sort, List<String> sortProperties);

    Page<Message> findMessageByRecipientId(Long recipientId, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Message> findMessageByRecipientId(Long recipientId, String sort, List<String> sortProperties);

    List<Message> findMessageByRecipientId(Long recipientId);

    boolean deleteMessageAndMaybeDialog(Long messageId);

    boolean deleteMessageAndMaybeDialog(Long messageId, Long senderId);

    Message saveMessageAndMaybeCreateNewDialog(String text, Long senderId, Long recipientId, Date createDateUTC);

    List<Message> findMessageByInterlocutor(Long interlocutor, Long second);

    Page<Message> findMessageByInterlocutor(Long interlocutor, Long second, Integer page, Integer size, String sort, List<String> sortProperties);
}
