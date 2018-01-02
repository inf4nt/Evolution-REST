package evolution.controller;

import evolution.dto.modelOld.FeedDTO;
import evolution.dto.modelOld.FeedForSaveDTO;
import evolution.dto.modelOld.FeedForUpdateDTO;
import evolution.rest.api.FeedRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/feed")
@CrossOrigin
public class FeedRestController {

    private final FeedRestService feedRestService;

    @Autowired
    public FeedRestController(FeedRestService feedRestService) {
        this.feedRestService = feedRestService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<FeedDTO>> findAll() {
        return feedRestService.findAll();
    }

    @GetMapping
    public ResponseEntity<Page<FeedDTO>> findAllPage(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size,
                                                     @RequestParam(required = false) String sortType,
                                                     @RequestParam(required = false) List<String> sortProperties) {
        return feedRestService.findAll(page, size, sortType, sortProperties);
    }

    @GetMapping("/friends/list")
    public ResponseEntity<List<FeedDTO>> findMyFriendsFeed() {
        return feedRestService.findMyFriendsFeed();
    }

    @GetMapping("/friends")
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(@RequestParam(required = false) Integer page,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) String sortType,
                                                           @RequestParam(required = false) List<String> sortProperties) {
        return feedRestService.findMyFriendsFeed(page, size, sortType, sortProperties);
    }

    @GetMapping("/forme/list")
    public ResponseEntity<List<FeedDTO>> findFeedsForMe() {
        return feedRestService.findFeedsForMe();
    }

    @GetMapping("/forme")
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String sortType,
                                                        @RequestParam(required = false) List<String> sortProperties) {
        return feedRestService.findFeedsForMe(page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FeedDTO> findOne(@PathVariable Long id) {
        return feedRestService.findOne(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FeedDTO> delete(@PathVariable Long id) {
        return feedRestService.delete(id);
    }

    @PostMapping
    public ResponseEntity<FeedDTO> post(@RequestBody FeedForSaveDTO feed) {
        return feedRestService.create(feed);
    }

    @PutMapping
    public ResponseEntity<FeedDTO> put(@RequestBody FeedForUpdateDTO feed) {
        return feedRestService.update(feed);
    }

    @GetMapping("/friends/user/{id}/list")
    public ResponseEntity<List<FeedDTO>> findMyFriendsFeed2(@PathVariable Long id) {
        return feedRestService.findMyFriendsFeed(id);
    }

    @GetMapping("/friends/user/{id}")
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed2(@PathVariable Long id,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) String sortType,
                                                            @RequestParam(required = false) List<String> sortProperties) {
        return feedRestService.findMyFriendsFeed(id, page, size, sortType, sortProperties);
    }

    @GetMapping("/forme/user/{id}/list")
    public ResponseEntity<List<FeedDTO>> findFeedsForMe2(@PathVariable Long id) {
        return feedRestService.findFeedsForMe(id);
    }

    @GetMapping("/forme/user/{id}")
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe2(@PathVariable Long id,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size,
                                                         @RequestParam(required = false) String sortType,
                                                         @RequestParam(required = false) List<String> sortProperties) {
        return feedRestService.findFeedsForMe(id, page, size, sortType, sortProperties);
    }
}
