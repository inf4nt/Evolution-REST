package evolution.rest.api;

import evolution.dto.model.DialogDTO;
import evolution.dto.model.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
public interface DialogRestService {

    ResponseEntity<List<DialogDTO>> findAll();

    ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<DialogDTO> findOne(Long dialogId);

    ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Page<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId);

    ResponseEntity<HttpStatus> delete(Long id);
}
