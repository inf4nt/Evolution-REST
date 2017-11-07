package evolution.rest;

import evolution.dto.model.DialogDTO;
import evolution.rest.api.DialogRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
@Service
public class DialogRestServiceImpl implements DialogRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
//        Pageable pageable = helperDataService.getPageableForDialog(page, size, sort, sortProperties);
//        Page<Dialog> p = dialogDataService.findAll(pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<DialogDTO> findOne(Long dialogId) {
//        Optional<Dialog> optional = dialogDataService.findOne(dialogId);
//        return helperRestService.getResponseForOptional(optional);
        return null;
    }

    @Override
    public ResponseEntity<DialogDTO> findDialogByDialogId(Long dialogId) {
//        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
//        Optional<Dialog> optional = dialogDataService.findDialogByIdAndSomeUser(dialogId, auth.getId());
//        return helperRestService.getResponseForOptional(optional);
        return null;
    }

    @Override
    public ResponseEntity<DialogDTO> findDialogByDialogIdAuthUserInDialog(Long dialogId) {
        return null;
    }
}
