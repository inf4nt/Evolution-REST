package evolution.controller;

import evolution.model.Dialog;
import evolution.rest.api.DialogRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<Dialog>> findAll(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer size,
                                                @RequestParam(required = false) String sortType,
                                                @RequestParam(required = false) List<String> sortProperties) {
        return dialogRestService.findAll(page, size, sortType, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Dialog> findOne(@PathVariable Long id) {
        return dialogRestService.findOne(id);
    }
}
