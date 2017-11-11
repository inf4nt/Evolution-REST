package evolution.rest.api;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserForUpdate;
import evolution.dto.model.UserFullDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserRestService {

    ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<UserFullDTO>> findAllFull(String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserFullDTO>> findAllFullPage(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<UserDTO> findOne(Long userId);

    ResponseEntity<UserFullDTO> findOneFull(Long userId);

    ResponseEntity<UserDTO> findByUsername(String username);

    ResponseEntity<UserFullDTO> findByUsernameFull(String username);

    ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties);

    ResponseEntity<HttpStatus> createNewUser(UserDTOForSave user);

    ResponseEntity<HttpStatus> update(UserForUpdate user);

    ResponseEntity<HttpStatus> delete(Long id);

    ResponseEntity<HttpStatus> block(Long id);

    ResponseEntity<HttpStatus> anBlock(Long id);

    ResponseEntity<HttpStatus> activated(String key);

    ResponseEntity<HttpStatus> exist(String username);
}
