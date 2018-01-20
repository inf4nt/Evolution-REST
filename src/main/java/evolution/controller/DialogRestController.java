package evolution.controller;

import evolution.dto.model.DialogDTO;
import evolution.dto.model.DialogDTOLazy;
import evolution.dto.model.MessageDTO;
import evolution.rest.api.DialogRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<DialogDTO>> findAll() {
        return dialogRestService.findAll();
    }

    @GetMapping(value = "/lazy")
    public ResponseEntity<List<DialogDTOLazy>> findAllList() {
        return dialogRestService.findAllLazy();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<DialogDTO>> findAllPage(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) String sortType,
                                                       @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findAll(page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/page/lazy")
    public ResponseEntity<Page<DialogDTOLazy>> findAllPageLazy(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer size,
                                                               @RequestParam(required = false) String sortType,
                                                               @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findAllLazy(page, size, sortType, sortProperties);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<DialogDTO> findOne(@PathVariable Long id) {
        return dialogRestService.findOne(id);
    }

    @GetMapping(value = "/{id}/lazy")
    public ResponseEntity<DialogDTOLazy> findOneLazy(@PathVariable Long id) {
        return dialogRestService.findOneLazy(id);
    }

    @GetMapping(value = "/{id}/message")
    public ResponseEntity<List<MessageDTO>> findMessageByDialogId(@PathVariable Long id,
                                                                  @RequestParam(required = false) String sortType,
                                                                  @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findMessageByDialog(id, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}/message/page")
    public ResponseEntity<Page<MessageDTO>> findMessageByDialogIdPage(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findMessageByDialog(id, page, size, sortType, sortProperties);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        return dialogRestService.delete(id);
    }

    @GetMapping(value = "/for-user/{id}")
    public ResponseEntity<List<DialogDTO>> findDialogByUser(@PathVariable Long id) {
        return dialogRestService.findDialogsByUserId(id);
    }

    @GetMapping(value = "/for-user/{id}/list")
    public ResponseEntity<List<DialogDTO>> findDialogByUser(@PathVariable Long id,
                                                            @RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findDialogsByUserId(id, sort, sortProperties);
    }

    @GetMapping(value = "/for-user/{id}/page")
    public ResponseEntity<Page<DialogDTO>> findDialogByUser(@PathVariable Long id,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findDialogsByUserId(id, page, size, sort, sortProperties);
    }
}
