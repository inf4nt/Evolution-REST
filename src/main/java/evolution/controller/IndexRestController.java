package evolution.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Infant on 04.09.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/index")
public class IndexRestController {

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok().build();
    }

}
