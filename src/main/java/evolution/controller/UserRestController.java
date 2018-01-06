package evolution.controller;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOLazy;
import evolution.dto.model.UserSaveDTO;
import evolution.dto.model.UserUpdateDTO;
import evolution.dto.model.UserSetPasswordDTO;
import evolution.rest.api.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAll(sort, sortProperties);
    }

    @GetMapping(value = "/lazy")
    public ResponseEntity<List<UserDTOLazy>> findAllLazy(@RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAllLazy(sort, sortProperties);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserDTO>> findAllPage(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size,
                                                     @RequestParam(required = false) String sort,
                                                     @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAll(page, size, sort, sortProperties);
    }

    @GetMapping(value = "/page/lazy")
    public ResponseEntity<Page<UserDTOLazy>> findAllPageLazy(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false) String sort,
                                                             @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAllLazy(page, size, sort, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
        return userRestService.findOne(id);
    }

    @GetMapping(value = "/{id}/lazy")
    public ResponseEntity<UserDTOLazy> findOneLazy(@PathVariable Long id) {
        return userRestService.findOneLazy(id);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<UserDTOLazy> save2(@RequestBody UserSaveDTO user) {
        return save(user);
    }

    @PostMapping
    public ResponseEntity<UserDTOLazy> save(@RequestBody UserSaveDTO user) {
        return userRestService.createNewUser(user);
    }

    @PutMapping
    public ResponseEntity<UserDTOLazy> update(@RequestBody UserUpdateDTO user) {
        return userRestService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return userRestService.delete(id);
    }

    @DeleteMapping(value = "/list")
    public ResponseEntity deleteById(@RequestParam List<Long> ids) {
        return userRestService.delete(ids);
    }

    @GetMapping(value = "/block/{id}")
    public ResponseEntity blockUser(@PathVariable Long id) {
        return userRestService.block(id);
    }

    @GetMapping(value = "/unBlock/{id}")
    public ResponseEntity unBlock(@PathVariable Long id) {
        return userRestService.unBlock(id);
    }

    @GetMapping(value = "/activated/{key}")
    public ResponseEntity unBlock(@PathVariable String key) {
        return userRestService.activated(key);
    }

    @GetMapping(value = "/exist")
    public ResponseEntity<HttpStatus> exist(@RequestParam String username) {
        return userRestService.exist(username);
    }

    @PutMapping(value = "/set-password")
    public ResponseEntity<HttpStatus> setPassword(@RequestBody UserSetPasswordDTO userSetPasswordDTO) {
        return userRestService.setPassword(userSetPasswordDTO);
    }
}
