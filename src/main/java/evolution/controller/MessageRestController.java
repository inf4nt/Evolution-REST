package evolution.controller;

import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import evolution.rest.api.MessageRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Infant on 28.10.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/message")
public class MessageRestController {

    private final MessageRestService messageRestService;

    @Autowired
    public MessageRestController(MessageRestService messageRestService) {
        this.messageRestService = messageRestService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> findAll(@RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findAll(sortType, sortProperties);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<MessageDTO>> findAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findAllMessage(page, size, sortType, sortProperties);
    }


    @PutMapping
    public ResponseEntity<MessageDTO> putMessage(@RequestBody MessageUpdateDTO message) {
        return messageRestService.update(message);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageSaveDTO message) {
        return messageRestService.save(message);
    }

    @GetMapping(value = "/interlocutor/{id}/page")
    public ResponseEntity<Page<MessageDTO>> findMessageByInterlocutor(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageByInterlocutor(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/interlocutor/{id}")
    public ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(@PathVariable Long id,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageByInterlocutor(id, sortType, sortProperties);
    }

    @GetMapping(value = "/recipient/user/{id}/page")
    public ResponseEntity<Page<MessageDTO>> findMessageByRecipient(@PathVariable Long id,
                                                                   @RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false) Integer size,
                                                                   @RequestParam(required = false) String sortType,
                                                                   @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageRecipientId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/recipient/user/{id}")
    public ResponseEntity<List<MessageDTO>> findMessageByRecipient(@PathVariable Long id,
                                                                   @RequestParam(required = false) String sortType,
                                                                   @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageRecipientId(id, sortType, sortProperties);
    }

    @GetMapping(value = "/sender/user/{id}/page")
    public ResponseEntity<Page<MessageDTO>> findMessageBySender(@PathVariable Long id,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(required = false) String sortType,
                                                                @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageSenderId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/sender/user/{id}")
    public ResponseEntity<List<MessageDTO>> findMessageBySender(@PathVariable Long id,
                                                                @RequestParam(required = false) String sortType,
                                                                @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageSenderId(id, sortType, sortProperties);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        return messageRestService.delete(id);
    }

    @DeleteMapping(value = "/list")
    public ResponseEntity<HttpStatus> delete(@RequestParam List<Long> ids) {
        return messageRestService.delete(ids);
    }

    @GetMapping(value = "/last-message-dialog/user/{id}")
    public ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(@PathVariable Long id,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findLastMessageInMyDialog(id, sortType, sortProperties);
    }

    @GetMapping(value = "/last-message-dialog/user/{id}/page")
    public ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findLastMessageInMyDialog(id, page, size, sortType, sortProperties);
    }
}
