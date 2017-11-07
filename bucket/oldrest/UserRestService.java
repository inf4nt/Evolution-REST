package evolution.common.oldrest;

import evolution.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 21.10.2017.
 */
public interface UserRestService {

    ResponseEntity<Page<User>> findAll(Integer page, Integer size, String sort, List<String> sortProperties, boolean lazy);

    ResponseEntity<User> findOne(Long id, boolean lazy);

    ResponseEntity<HttpStatus> createNewUser(User user);

    ResponseEntity<HttpStatus> update(User user);

    ResponseEntity<HttpStatus> delete(Long id);

    ResponseEntity<HttpStatus> block(Long id);

    ResponseEntity<HttpStatus> anBlock(Long id);

    ResponseEntity<HttpStatus> activated(String key);

    ResponseEntity<HttpStatus> exist(String username);
}
