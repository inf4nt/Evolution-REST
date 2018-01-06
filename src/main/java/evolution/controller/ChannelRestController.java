package evolution.controller;

import evolution.dto.model.ChannelDTO;
import evolution.dto.model.ChannelDTOLazy;
import evolution.dto.model.MessageChannelDTO;
import evolution.rest.api.ChannelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/channel")
@CrossOrigin
public class ChannelRestController {

    private final ChannelRestService channelRestService;

    @Autowired
    public ChannelRestController(ChannelRestService channelRestService) {
        this.channelRestService = channelRestService;
    }

    @GetMapping
    public ResponseEntity<List<ChannelDTO>> findAll() {
        return channelRestService.findAllChannel();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<ChannelDTO>> findAll(@RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findAllChannel(sortType, sortProperties);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ChannelDTO>> findAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) String sortType,
                                                    @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findAllChannel(page, size, sortType, sortProperties);
    }


    @GetMapping(value = "/lazy")
    public ResponseEntity<List<ChannelDTOLazy>> findAllLazy() {
        return channelRestService.findAllChannelLazy();
    }

    @GetMapping(value = "/lazy/list")
    public ResponseEntity<List<ChannelDTOLazy>> findAllLazy(@RequestParam(required = false) String sortType,
                                                            @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findAllChannelLazy(sortType, sortProperties);
    }

    @GetMapping(value = "/lazy/page")
    public ResponseEntity<Page<ChannelDTOLazy>> findAllLazy(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) String sortType,
                                                            @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findAllChannelLazy(page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}/message")
    public ResponseEntity<List<MessageChannelDTO>> findMessageByChannel(@PathVariable Long id) {
        return channelRestService.findMessageByChannelId(id);
    }

    @GetMapping(value = "/{id}/message/list")
    public ResponseEntity<List<MessageChannelDTO>> findMessageByChannel(@PathVariable Long id,
                                                                        @RequestParam(required = false) String sortType,
                                                                        @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findMessageByChannelId(id, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}/message/page")
    public ResponseEntity<Page<MessageChannelDTO>> findMessageByChannel(@PathVariable Long id,
                                                                        @RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size,
                                                                        @RequestParam(required = false) String sortType,
                                                                        @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findMessageByChannelId(id, page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/for-user/{id}")
    public ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(@PathVariable Long id) {
        return channelRestService.findChannelForChannelUser(id);
    }

    @GetMapping(value = "/for-user/{id}/list")
    public ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(@PathVariable Long id,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findChannelForChannelUser(id, sortType, sortProperties);
    }

    @GetMapping(value = "/for-user/{id}/page")
    public ResponseEntity<Page<ChannelDTO>> findChannelForChannelUser(@PathVariable Long id,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(required = false) String sortType,
                                                                      @RequestParam(required = false) List<String> sortProperties) {
        return channelRestService.findChannelForChannelUser(id, page, size, sortType, sortProperties);
    }

}
