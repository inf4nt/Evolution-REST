package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.DialogBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.DialogDTO;
import evolution.dto.model.MessageDTO;
import evolution.rest.api.DialogRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 05.11.2017.
 */
@Service
public class DialogRestServiceImpl implements DialogRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DialogBusinessService dialogBusinessService;

    @Autowired
    public DialogRestServiceImpl(DialogBusinessService dialogBusinessService) {
        this.dialogBusinessService = dialogBusinessService;
    }

    @Override
    public ResponseEntity<List<DialogDTO>> findAll() {
        List<DialogDTO> list = dialogBusinessService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<DialogDTO> p = dialogBusinessService.findAll(page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<DialogDTO> findOne(Long dialogId) {
        Optional<DialogDTO> dialogDTO = dialogBusinessService.findOne(dialogId);
        if(dialogDTO.isPresent()) {
            return ResponseEntity.ok(dialogDTO.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = dialogBusinessService.findMessageByDialog(dialogId, page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = dialogBusinessService.findMessageByDialogAndUserId(dialogId, page, size, sort, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialogAndUserId(Long dialogId) {
        List<MessageDTO> list = dialogBusinessService.findMessageByDialogAndUserId(dialogId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        BusinessServiceExecuteResult b = dialogBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.ok().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED){
            return ResponseEntity.status(417).build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
}
