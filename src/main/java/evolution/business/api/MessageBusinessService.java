package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
import evolution.dto.model.MessageDTOFull;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Infant on 08.11.2017.
 */
public interface MessageBusinessService {

    List<MessageDTO> findAll();

    Page<MessageDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findAll(String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogId(Long dialogId);

    Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogId(Long dialogId, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId);

    Page<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, String sortType, List<String> sortProperties);

    BusinessServiceExecuteResult<MessageDTOForSave> createNewMessage(MessageDTOForSave messageDTOForSave);

    BusinessServiceExecuteResult<MessageDTO> createNewMessage2(MessageDTOForSave messageDTOForSave);

    BusinessServiceExecuteResult<MessageDTOFull> createNewMessage3(MessageDTOForSave messageDTOForSave);

    BusinessServiceExecuteResult<MessageDTOForSave> createMessage(Long senderId, Long recipientId, String text);

    BusinessServiceExecuteResult<MessageDTO> createMessage2(Long senderId, Long recipientId, String text);

    BusinessServiceExecuteResult<MessageDTOFull> createMessage3(Long senderId, Long recipientId, String text);
}