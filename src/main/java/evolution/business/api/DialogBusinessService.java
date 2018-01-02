package evolution.business.api;


import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.DialogDTO;
import evolution.dto.model.DialogDTOLazy;
import evolution.dto.model.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface DialogBusinessService {

    BusinessServiceExecuteResult<List<DialogDTO>> findDialogsByUserId(Long userId);

    Optional<DialogDTO> findOne(Long id);

    Optional<DialogDTOLazy> findOneLazy(Long id);

    List<DialogDTO> findAll();

    List<DialogDTO> findAll(String sortType, List<String> sortProperties);

    Page<DialogDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<DialogDTOLazy> findAllLazy();

    List<DialogDTOLazy> findAllLazy(String sortType, List<String> sortProperties);

    Page<DialogDTOLazy> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties);

    BusinessServiceExecuteResult<HttpStatus> delete(Long id);

    List<MessageDTO> findMessageByDialog(Long dialogId);

    List<MessageDTO> findMessageByDialog(Long dialogId, String sortType, List<String> sortProperties);

    Page<MessageDTO> findMessageByDialog(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties);
}
