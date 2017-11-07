package evolution.rest.api;

import evolution.dto.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserRestService {

    ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<UserDTO> findOne(Long userId);

    ResponseEntity<UserDTO> findByUsername(String username);

    ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties);

    ResponseEntity<HttpStatus> createNewUser(UserDTO user);

    ResponseEntity<HttpStatus> update(UserDTO user);

    ResponseEntity<HttpStatus> updateAuthUser(UserDTO user);

    ResponseEntity<HttpStatus> delete(Long id);

    ResponseEntity<HttpStatus> block(Long id);

    ResponseEntity<HttpStatus> anBlock(Long id);

    ResponseEntity<HttpStatus> activated(String key);

    ResponseEntity<HttpStatus> exist(String username);
}
