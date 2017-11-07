package evolution.rest.api;

import evolution.dto.model.DialogDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
public interface DialogRestService {

    ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<DialogDTO> findOne(Long dialogId);

    ResponseEntity<DialogDTO> findDialogByDialogId(Long dialogId);

    ResponseEntity<DialogDTO> findDialogByDialogIdAuthUserInDialog(Long dialogId);
}
