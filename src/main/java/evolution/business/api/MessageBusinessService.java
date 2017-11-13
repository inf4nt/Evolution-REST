package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.MessageDTO;
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
}
