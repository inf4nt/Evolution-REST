package evolution.module.message.rest.api;

import evolution.module.message.dto.DialogDTO;
import evolution.module.message.dto.DialogDTOLazy;
import evolution.module.message.dto.MessageDTO;
import evolution.rest.AbstractRestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
public interface DialogRestService extends AbstractRestService {

    ResponseEntity<List<DialogDTO>> findDialogsByUserId(Long id);

    ResponseEntity<List<DialogDTO>> findDialogsByUserId(Long id, String sort, List<String> sortProperties);

    ResponseEntity<Page<DialogDTO>> findDialogsByUserId(Long id, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<DialogDTO>> findAll();

    ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<DialogDTOLazy>> findAllLazy();

    ResponseEntity<Page<DialogDTOLazy>> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<DialogDTO> findOne(Long dialogId);

    ResponseEntity<DialogDTOLazy> findOneLazy(Long dialogId);

    ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId);

    ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId, String sort, List<String> sortProperties);

    ResponseEntity<HttpStatus> delete(Long id);
}
