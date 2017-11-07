package evolution.controller;

import evolution.dto.model.MessageDTO;
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
    public ResponseEntity<Page<MessageDTO>> findAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findAllMessage(page, size, sortType, sortProperties);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> postMessage(@RequestBody MessageDTO message) {
        return messageRestService.save(message);
    }

    @GetMapping(value = "/interlocutor/{id}")
    public ResponseEntity<Page<MessageDTO>> findMessageByInterlocutorId(@PathVariable Long id,
                                                                     @RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) String sortType,
                                                                     @RequestParam(required = false) List<String> sortProperties) {
        return messageRestService.findMessageByAuthUserAndRecipientId(id, page, size, sortType, sortProperties);
    }
}
