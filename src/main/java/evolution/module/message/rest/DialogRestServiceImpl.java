package evolution.message.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.message.business.api.DialogBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.message.dto.DialogDTO;
import evolution.message.dto.DialogDTOLazy;
import evolution.message.dto.MessageDTO;
import evolution.message.rest.api.DialogRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<DialogDTO>> findDialogsByUserId(Long id) {
        return response(dialogBusinessService.findDialogsByUserId(id));
    }

    @Override
    public ResponseEntity<List<DialogDTO>> findDialogsByUserId(Long id, String sort, List<String> sortProperties) {
        return response(dialogBusinessService.findDialogsByUserId(id, sort, sortProperties));
    }

    @Override
    public ResponseEntity<Page<DialogDTO>> findDialogsByUserId(Long id, Integer page, Integer size, String sort, List<String> sortProperties) {
        return response(dialogBusinessService.findDialogsByUserId(id, page, size, sort, sortProperties));
    }

    @Override
    public ResponseEntity<List<DialogDTO>> findAll() {
        List<DialogDTO> list = dialogBusinessService.findAll();
        return response(list);
    }

    @Override
    public ResponseEntity<Page<DialogDTO>> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<DialogDTO> p = dialogBusinessService.findAll(page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<DialogDTOLazy>> findAllLazy() {
        return response(dialogBusinessService.findAllLazy());
    }

    @Override
    public ResponseEntity<Page<DialogDTOLazy>> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties) {
        return response(dialogBusinessService.findAllLazy(page, size, sort, sortProperties));
    }

    @Override
    public ResponseEntity<DialogDTO> findOne(Long dialogId) {
        Optional<DialogDTO> o = dialogBusinessService.findOne(dialogId);
        return response(o);
    }

    @Override
    public ResponseEntity<DialogDTOLazy> findOneLazy(Long dialogId) {
        return response(dialogBusinessService.findOneLazy(dialogId));
    }

    @Override
    public ResponseEntity<Page<MessageDTO>> findMessageByDialog(Long dialogId, Integer page, Integer size, String sort, List<String> sortProperties) {
        Page<MessageDTO> p = dialogBusinessService.findMessageByDialog(dialogId, page, size, sort, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId) {
        return response(dialogBusinessService.findMessageByDialog(dialogId));
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findMessageByDialog(Long dialogId, String sort, List<String> sortProperties) {
        return response(dialogBusinessService.findMessageByDialog(dialogId, sort, sortProperties));
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        BusinessServiceExecuteResult b = dialogBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.EXPECTATION_FAILED){
            return ResponseEntity.status(417).build();
        }
        return ResponseEntity.status(500).build();
    }
}
