package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
import evolution.dto.model.MessageDTOFull;
import evolution.dto.model.MessageForUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

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

    BusinessServiceExecuteResult<MessageDTO> createNewMessage(MessageDTOForSave messageDTOForSave);

    BusinessServiceExecuteResult<MessageDTO> createMessage(Long senderId, Long recipientId, String text);

    BusinessServiceExecuteResult delete(Long id);

    BusinessServiceExecuteResult<MessageDTO> update(MessageForUpdateDTO messageForUpdateDTO);

    List<MessageDTO> findLastMessageInMyDialog(Long iam);

    Page<MessageDTO> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageBySenderId(Long iam);

    Page<MessageDTO> findMessageBySenderId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByRecipientId(Long iam);

    Page<MessageDTO> findMessageByRecipientId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByInterlocutor(Long interlocutor);

    Page<MessageDTO> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties);
}