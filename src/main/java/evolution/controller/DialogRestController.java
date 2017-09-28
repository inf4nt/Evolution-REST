package evolution.controller;

import evolution.data.DialogDataService;
import evolution.model.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/dialog")
public class DialogRestController {

    private final DialogDataService dialogDataService;

    @Autowired
    public DialogRestController(DialogDataService dialogDataService) {
        this.dialogDataService = dialogDataService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<Dialog> dialogs = dialogDataService.findAll();
        if(dialogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dialogs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional optional = dialogDataService.findOne(id);
        if (!optional.isPresent())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(optional.get());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        try {
            dialogDataService.delete(new Dialog(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity deleteByIdGET(@PathVariable Long id) {
        try {
            dialogDataService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
