package evolution.controller;

import evolution.dto.model.DialogDTO;
import evolution.dto.model.DialogFullDTO;
import evolution.dto.model.MessageDTO;
import evolution.rest.api.DialogRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedEntityGraph;
import java.util.List;

/**
 * Created by Infant on 05.11.2017.
 */
@RestController
@RequestMapping(value = "/dialog")
@CrossOrigin
public class DialogRestController {

    private final DialogRestService dialogRestService;

    @Autowired
    public DialogRestController(DialogRestService dialogRestService) {
        this.dialogRestService = dialogRestService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<DialogFullDTO>> findAll2() {
        return dialogRestService.findAll();
    }

    @GetMapping
    public ResponseEntity<Page<DialogFullDTO>> findAll(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) String sortType,
                                                       @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findAll(page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DialogFullDTO> findOne(@PathVariable Long id) {
        return dialogRestService.findOne(id);
    }

    @GetMapping(value = "/{id}/message")
    public ResponseEntity<Page<MessageDTO>> findMessageByDialog(@PathVariable Long id,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(required = false) String sortType,
                                                                @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findMessageByDialogAndUserId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}/message/list")
    public ResponseEntity<List<MessageDTO>> findMessageByDialog2(@PathVariable Long id) {
        return dialogRestService.findMessageByDialogAndUserId(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        return dialogRestService.delete(id);
    }

}
