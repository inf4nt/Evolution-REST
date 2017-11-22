package evolution.business.api;


import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.DialogDTO;
import evolution.dto.model.DialogFullDTO;
import evolution.dto.model.MessageDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface DialogBusinessService {

    Optional<DialogFullDTO> findOne(Long id);

    List<DialogFullDTO> findAll();

    List<DialogFullDTO> findAll(String sortType, List<String> sortProperties);

    Page<DialogFullDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    BusinessServiceExecuteResult delete(Long id);

    List<MessageDTO> findMessageByDialog(Long dialogId);

    List<MessageDTO> findMessageByDialog(Long dialogId, String sortType, List<String> sortProperties);

    Page<MessageDTO> findMessageByDialog(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageDTO> findMessageByDialogAndUserId(Long dialogId);

    List<MessageDTO> findMessageByDialogAndUserId(Long dialogId, String sortType, List<String> sortProperties);

    Page<MessageDTO> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);
}
