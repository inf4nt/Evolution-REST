package evolution.controller;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserForSaveDTO;
import evolution.dto.model.UserForUpdateDTO;
import evolution.dto.model.UserFullDTO;
import evolution.rest.api.UserRestService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAll(page, size, sort, sortProperties);
    }

    @GetMapping(value = "/list/lazy")
    public ResponseEntity<List<UserFullDTO>> findAllList(@RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAllFull(sort, sortProperties);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDTO>> findAll2(@RequestParam(required = false) String sort,
                                                      @RequestParam(required = false) List<String> sortProperties) {
        return userRestService.findAll2(sort, sortProperties);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
        return userRestService.findOne(id);
    }

    @GetMapping(value = "/{id}/lazy")
    public ResponseEntity<evolution.dto.model2.UserDTO> findOneLazy(@PathVariable Long id) {
       return userRestService.findOneFull(id);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<UserFullDTO> save2(@RequestBody UserForSaveDTO user) {
        return save(user);
    }

    @PostMapping
    public ResponseEntity<UserFullDTO> save(@RequestBody UserForSaveDTO user) {
        return userRestService.createNewUser(user);
    }

    @PutMapping
    public ResponseEntity<evolution.dto.model2.UserDTO> update(@RequestBody UserForUpdateDTO user) {
        return userRestService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return userRestService.delete(id);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteById(@RequestParam List<Long> ids) {
        return userRestService.delete(ids);
    }

    @GetMapping(value = "/block/{id}")
    public ResponseEntity blockUser(@PathVariable Long id) {
        return userRestService.block(id);
    }

    @GetMapping(value = "/anBlock/{id}")
    public ResponseEntity anBlockUser(@PathVariable Long id) {
        return userRestService.anBlock(id);
    }

    @GetMapping(value = "/activated/{key}")
    public ResponseEntity anBlockUser(@PathVariable String key) {
        return userRestService.activated(key);
    }

    @GetMapping(value = "/exist")
    public ResponseEntity<HttpStatus> exist(@RequestParam String username) {
        return userRestService.exist(username);
    }

    @PutMapping(value = "/set-password")
    public ResponseEntity<UserFullDTO> setPassword(@RequestBody UserFullDTO userFullDTO) {
        return userRestService.setPassword(userFullDTO);
    }
}
