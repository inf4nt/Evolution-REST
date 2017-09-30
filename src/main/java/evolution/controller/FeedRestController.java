package evolution.controller;

import evolution.data.FeedDataService;
import evolution.model.feed.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Infant on 24.09.2017.
 */
@RestController
@RequestMapping(value = "/feed")
@CrossOrigin
public class FeedRestController {

    private final FeedDataService feedDataService;

    @Autowired
    public FeedRestController(FeedDataService feedDataService) {
        this.feedDataService = feedDataService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<Feed> list = feedDataService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity findFeedByUSer(@PathVariable Long id) {
        List<Feed> list = feedDataService.findMyFeeds(id);
        if(list.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }


}
