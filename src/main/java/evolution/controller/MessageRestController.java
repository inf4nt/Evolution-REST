package evolution.controller;

import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
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

    @GetMapping(value = "/list")
    public ResponseEntity<List<MessageDTO>> findAllMessage() {
        return messageRestService.findAll();
    }

    @GetMapping
    public ResponseEntity<Page<MessageDTO>> findAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findAllMessage(page, size, sortType, sortProperties);
    }

    //todo return messageDTO
    @PostMapping
    public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageDTOForSave message) {
        return messageRestService.save(message);
    }

    @GetMapping(value = "/interlocutor/{id}")
    public ResponseEntity<Page<MessageDTO>> findMessageByInterlocutor(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageByInterlocutor(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/interlocutor/{id}/list")
    public ResponseEntity<List<MessageDTO>> findMessageByInterlocutor(@PathVariable Long id) {
        return messageRestService.findMessageByInterlocutor(id);
    }

    @GetMapping(value = "/recipient/user/{id}")
    public ResponseEntity<Page<MessageDTO>> findMessageByRecipient(@PathVariable Long id,
                                                                   @RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false) Integer size,
                                                                   @RequestParam(required = false) String sortType,
                                                                   @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageRecipientId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/sender/user/{id}")
    public ResponseEntity<Page<MessageDTO>> findMessageBySender(@PathVariable Long id,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(required = false) String sortType,
                                                                @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageSenderId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/dialog/{id}")
    public ResponseEntity<Page<MessageDTO>> findMessageByDialog(@PathVariable Long id,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(required = false) String sortType,
                                                                @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageByDialog(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/dialog/{id}/list")
    public ResponseEntity<List<MessageDTO>> findMessageByDialog(@PathVariable Long id) {
        return messageRestService.findMessageByDialog(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        return messageRestService.delete(id);
    }

    @GetMapping(value = "/last-message-dialog/user/{id}/list")
    public ResponseEntity<List<MessageDTO>> findLastMessageInMyDialog(@PathVariable Long id) {
        return messageRestService.findLastMessageInMyDialog(id);
    }

    @GetMapping(value = "/last-message-dialog/user/{id}")
    public ResponseEntity<Page<MessageDTO>> findLastMessageInMyDialog(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findLastMessageInMyDialog(id, page, size, sortType, sortProperties);
    }
}
