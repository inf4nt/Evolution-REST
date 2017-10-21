package evolution.controller;

import evolution.model.User;
import evolution.rest.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Infant on 21.10.2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserRestController {

    private final UserRestService userRestService;

    @Autowired
    public UserRestController(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String sort,
                                              @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAll(page, size, sort, sortProperties);
    }

    @GetMapping(value = "/lazy")
    public ResponseEntity<List<User>> findAllLoadLazy(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size,
                                                      @RequestParam(required = false) String sort,
                                                      @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAllLoadLazy(page, size, sort, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        return userRestService.findOne(id);
    }

    @GetMapping(value = "/{id}/lazy")
    public ResponseEntity<User> findOneLoadLazy(@PathVariable Long id) {
        return userRestService.findOneLoadLazy(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody User user) {
        return userRestService.createNewUser(user);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody User user) {
        return userRestService.update(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        return userRestService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/block/{id}")
    public ResponseEntity<HttpStatus> blockUser(@PathVariable Long id) {
        return userRestService.block(id);
    }

    @GetMapping(value = "/anBlock/{id}")
    public ResponseEntity<HttpStatus> anBlockUser(@PathVariable Long id) {
        return userRestService.anBlock(id);
    }

    @GetMapping(value = "/activated/{key}")
    public ResponseEntity<HttpStatus> anBlockUser(@PathVariable String key) {
        return userRestService.activated(key);
    }

    @GetMapping(value = "/exist/{username}")
    public ResponseEntity<HttpStatus> exist(@PathVariable String username) {
        return userRestService.exist(username);
    }
}
