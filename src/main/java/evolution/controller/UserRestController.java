package evolution.controller;

import evolution.data.UserDataService;
import evolution.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 29.09.2017.
 */
@RestController
@RequestMapping("/rest/user")
@CrossOrigin
public class UserRestController {

    private final UserDataService userDataService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserRestController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> optional = userDataService.findOne(id);
        if(optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<User> list = this.userDataService.findAll();
        if(list.isEmpty())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
