package evolution.rest.api;


import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOLazy;
import evolution.dto.model.UserSaveDTO;
import evolution.dto.model.UserUpdateDTO;
import evolution.dto.model.UserSetPasswordDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
public interface UserRestService extends AbstractRestService {

    // find all
    ResponseEntity<List<UserDTO>> findAll();

    ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all

    // find all lazy
    ResponseEntity<List<UserDTOLazy>> findAllLazy();

    ResponseEntity<List<UserDTOLazy>> findAllLazy(String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTOLazy>> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all lazy


    // find all is block
    ResponseEntity<List<UserDTO>> findAllAndIsBlock(boolean isBlock);

    ResponseEntity<List<UserDTO>> findAllAndIsBlock(boolean isBlock, String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTO>> findAllAndIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all is block

    // find all lazy is block
    ResponseEntity<List<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock);

    ResponseEntity<List<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock, String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all lazy is block


    // find all is active
    ResponseEntity<List<UserDTO>> findAllAndIsActive(boolean isActive);

    ResponseEntity<List<UserDTO>> findAllAndIsActive(boolean isActive, String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTO>> findAllAndIsActive(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all is active

    // find all lazy is active
    ResponseEntity<List<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive);

    ResponseEntity<List<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive, String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties);
    // find all lazy is active

    ResponseEntity<UserDTO> findOneAndIsBlock(Long id, boolean isBlock);

    ResponseEntity<UserDTOLazy> findOneAndIsBlockLazy(Long id, boolean isBlock);

    ResponseEntity<UserDTO> findOneAndIsActive(Long id, boolean isActive);

    ResponseEntity<UserDTOLazy> findOneAndIsActiveLazy(Long id, boolean isActive);

    ResponseEntity<UserDTO> findOne(Long id);

    ResponseEntity<UserDTOLazy> findOneLazy(Long id);

    ResponseEntity<UserDTO> findByUsername(String username);

    ResponseEntity<UserDTOLazy> findByUsernameLazy(String username);

    ResponseEntity<UserDTOLazy> createNewUser(UserSaveDTO user);

    ResponseEntity<UserDTOLazy> update(UserUpdateDTO user);

    ResponseEntity<HttpStatus> createNewUser2(UserSaveDTO user);

    ResponseEntity<HttpStatus> update2(UserUpdateDTO user);

    ResponseEntity<HttpStatus> delete(Long id);

    ResponseEntity<HttpStatus> delete(List<Long> id);

    ResponseEntity<HttpStatus> deleteAll();

    ResponseEntity<HttpStatus> block(Long id);

    ResponseEntity<HttpStatus> unBlock(Long id);

    ResponseEntity<HttpStatus> activated(String key);

    ResponseEntity<HttpStatus> setPassword(UserSetPasswordDTO userSetPasswordDTO);

    ResponseEntity<HttpStatus> exist(String username);

    ResponseEntity<UserDTOLazy> block2(Long id);

    ResponseEntity<UserDTOLazy> unBlock2(Long id);

    ResponseEntity<UserDTOLazy> activated2(String key);

    ResponseEntity<UserDTOLazy> setPassword2(UserSetPasswordDTO userSetPasswordDTO);
}
