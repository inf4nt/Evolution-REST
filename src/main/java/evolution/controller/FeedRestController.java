package evolution.controller;

import evolution.data.FeedDataService;
import evolution.model.feed.Feed;
import evolution.service.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Infant on 24.09.2017.
 */
@RestController(value = "/feed")
@CrossOrigin
public class FeedRestController {

    private final FeedDataService feedDataService;

    private final DtoConverter dtoConverter;

    @Autowired
    public FeedRestController(FeedDataService feedDataService, DtoConverter dtoConverter) {
        this.feedDataService = feedDataService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<Feed> list = feedDataService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtoConverter.feedToDTOList(list));
    }

}
