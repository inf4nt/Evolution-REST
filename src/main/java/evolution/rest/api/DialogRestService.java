package evolution.rest.api;

import evolution.model.Dialog;
import evolution.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
public interface DialogRestService {

    ResponseEntity<Page<Dialog>> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    ResponseEntity<Dialog> findOne(Long dialogId);

    ResponseEntity<Dialog> findDialogByIdAndAuthUser(Long dialogId);
}
