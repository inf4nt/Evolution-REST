package evolution.rest.api;

import evolution.model.User;
import evolution.model.UserAdditionalData;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserRestService {

    ResponseEntity<Page<User>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<User> findOne(Long userId);

    ResponseEntity<User> findByUsername(String username);

    ResponseEntity<List<User>> findAll(String sortType, List<String> sortProperties);

    ResponseEntity<HttpStatus> createNewUser(User user);

    ResponseEntity<HttpStatus> update(User user);

    ResponseEntity<HttpStatus> updateAuthUser(User user);

    ResponseEntity<HttpStatus> delete(Long id);

    ResponseEntity<HttpStatus> block(Long id);

    ResponseEntity<HttpStatus> anBlock(Long id);

    ResponseEntity<HttpStatus> activated(String key);

    ResponseEntity<HttpStatus> exist(String username);
}
