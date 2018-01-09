package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface MessageBusinessService {

    Optional<MessageDTO> findOne(Long id);

    List<MessageDTO> findAll();

    Page<MessageDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findAll(String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogId(Long dialogId);

    Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogId(Long dialogId, String sortType, List<String> sortProperties);

    BusinessServiceExecuteResult<MessageDTO> createNewMessage(MessageSaveDTO messageSaveDTO);

    BusinessServiceExecuteResult<MessageDTO> createMessage(Long senderId, Long recipientId, String text);

    BusinessServiceExecuteResult delete(Long id);

    BusinessServiceExecuteResult delete(List<Long> ids);

    BusinessServiceExecuteResult<MessageDTO> update(MessageUpdateDTO messageUpdateDTO);

    List<MessageDTO> findLastMessageInMyDialog(Long iam);

    List<MessageDTO> findLastMessageInMyDialog(Long iam, String sortType, List<String> sortProperties);

    Page<MessageDTO> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageBySenderId(Long iam);

    List<MessageDTO> findMessageBySenderId(Long iam, String sortType, List<String> sortProperties);

    Page<MessageDTO> findMessageBySenderId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByRecipientId(Long iam);

    List<MessageDTO> findMessageByRecipientId(Long iam, String sortType, List<String> sortProperties);

    Page<MessageDTO> findMessageByRecipientId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByInterlocutor(Long interlocutor);

    List<MessageDTO> findMessageByInterlocutor(Long interlocutor, String sort, List<String> sortProperties);

    Page<MessageDTO> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties);

    BusinessServiceExecuteResult<MessageDTO> createFirstMessageAfterRegistration(Long forUserId);
}