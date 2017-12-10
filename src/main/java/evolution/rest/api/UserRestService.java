package evolution.rest.api;

import evolution.dto.model.UserDTO;
import evolution.dto.model.UserForSaveDTO;
import evolution.dto.model.UserForUpdateDTO;
import evolution.dto.model.UserFullDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserRestService extends AbstractRestService {

    ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<UserFullDTO>> findAllFull(String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserFullDTO>> findAllFullPage(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<UserDTO> findOne(Long userId);

    ResponseEntity<UserFullDTO> findOneFull(Long userId);

    ResponseEntity<UserDTO> findByUsername(String username);

    ResponseEntity<UserFullDTO> findByUsernameFull(String username);

    ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties);

    ResponseEntity<UserFullDTO> createNewUser(UserForSaveDTO user);

    ResponseEntity<UserFullDTO> update(UserForUpdateDTO user);

    ResponseEntity delete(Long id);

    ResponseEntity delete(List<Long> id);

    ResponseEntity block(Long id);

    ResponseEntity anBlock(Long id);

    ResponseEntity activated(String key);

    ResponseEntity<HttpStatus> exist(String username);

    ResponseEntity<UserFullDTO> setPassword(UserFullDTO userFullDTO);
}
