package evolution.user.rest;



import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.module.user.business.api.UserBusinessService;
import evolution.module.user.dto.UserDTO;
import evolution.module.user.dto.UserDTOLazy;
import evolution.module.user.rest.api.UserRestService;
import evolution.user.dto.UserSaveDTO;
import evolution.user.dto.UserUpdateDTO;
import evolution.user.dto.UserSetPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class UserRestServiceImpl implements UserRestService {

    private final UserBusinessService userBusinessService;

    @Autowired
    public UserRestServiceImpl(UserBusinessService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll() {
        return response(userBusinessService.findAll());
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAll(sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAll(page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllLazy() {
        return response(userBusinessService.findAllLazy());
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllLazy(String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllLazy(sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTOLazy>> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllLazy(page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllAndIsBlock(boolean isBlock) {
        return response(userBusinessService.findAllAndIsBlock(isBlock));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllAndIsBlock(boolean isBlock, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsBlock(isBlock, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTO>> findAllAndIsBlock(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsBlock(isBlock, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock) {
        return response(userBusinessService.findAllAndIsBlockLazy(isBlock));
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsBlockLazy(isBlock, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTOLazy>> findAllAndIsBlockLazy(boolean isBlock, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsBlockLazy(isBlock, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllAndIsActive(boolean isActive) {
        return response(userBusinessService.findAllAndIsActive(isActive));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllAndIsActive(boolean isActive, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsActive(isActive, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTO>> findAllAndIsActive(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsActive(isActive, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive) {
        return response(userBusinessService.findAllAndIsActiveLazy(isActive));
    }

    @Override
    public ResponseEntity<List<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsActiveLazy(isActive, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTOLazy>> findAllAndIsActiveLazy(boolean isActive, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(userBusinessService.findAllAndIsActiveLazy(isActive, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<UserDTO> findOneAndIsBlock(Long id, boolean isBlock) {
        return response(userBusinessService.findOneAndIsBlock(id, isBlock));
    }

    @Override
    public ResponseEntity<UserDTOLazy> findOneAndIsBlockLazy(Long id, boolean isBlock) {
        return response(userBusinessService.findOneAndIsBlockLazy(id, isBlock));
    }

    @Override
    public ResponseEntity<UserDTO> findOneAndIsActive(Long id, boolean isActive) {
        return response(userBusinessService.findOneAndIsActive(id, isActive));
    }

    @Override
    public ResponseEntity<UserDTOLazy> findOneAndIsActiveLazy(Long id, boolean isActive) {
        return response(userBusinessService.findOneAndIsActiveLazy(id, isActive));
    }

    @Override
    public ResponseEntity<UserDTO> findOne(Long id) {
        return response(userBusinessService.findOne(id));
    }

    @Override
    public ResponseEntity<UserDTOLazy> findOneLazy(Long id) {
        return response(userBusinessService.findOneLazy(id));
    }

    @Override
    public ResponseEntity<UserDTO> findByUsername(String username) {
        return response(userBusinessService.findByUsername(username));
    }

    @Override
    public ResponseEntity<UserDTOLazy> findByUsernameLazy(String username) {
        return response(userBusinessService.findByUsernameLazy(username));
    }

    @Override
    public ResponseEntity<UserDTOLazy> createNewUser(UserSaveDTO user) {
        BusinessServiceExecuteResult<UserDTOLazy> b = userBusinessService.createNewUser2(user);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.status(201).body(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.USER_IS_ALREADY_EXIST_REGISTRATION_FAILED) {
            return ResponseEntity.status(417).build();
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<UserDTOLazy> update(UserUpdateDTO user) {
        BusinessServiceExecuteResult<UserDTOLazy> b = userBusinessService.update2(user);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> createNewUser2(UserSaveDTO user) {
        return ResponseEntity.status(createNewUser(user).getStatusCode()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> update2(UserUpdateDTO user) {
        return ResponseEntity.status(update2(user).getStatusCode()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        BusinessServiceExecuteResult<BusinessServiceExecuteStatus> b = userBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(List<Long> id) {
        BusinessServiceExecuteResult<BusinessServiceExecuteStatus> b = userBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAll() {
        BusinessServiceExecuteResult<BusinessServiceExecuteStatus> b = userBusinessService.deleteAll();
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> block(Long id) {
        ResponseEntity<UserDTOLazy> responseEntity = response(userBusinessService.sendBlockToUser(id));
        return ResponseEntity.status(responseEntity.getStatusCode()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> unBlock(Long id) {
        ResponseEntity<UserDTOLazy> responseEntity = response(userBusinessService.sendUnBlockToUser(id));
        return ResponseEntity.status(responseEntity.getStatusCode()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> activated(String key) {
        ResponseEntity<UserDTOLazy> responseEntity = response(userBusinessService.activatedUser(key));
        return ResponseEntity.status(responseEntity.getStatusCode()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> exist(String username) {
        return userBusinessService.findByUsername(username).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTOLazy> block2(Long id) {
        return response(userBusinessService.sendBlockToUser(id));
    }

    @Override
    public ResponseEntity<UserDTOLazy> unBlock2(Long id) {
        return response(userBusinessService.sendUnBlockToUser(id));
    }

    @Override
    public ResponseEntity<UserDTOLazy> activated2(String key) {
        return response(userBusinessService.activatedUser(key));
    }

    @Override
    public ResponseEntity<UserDTOLazy> setPassword2(UserSetPasswordDTO userSetPasswordDTO) {
        return response(userBusinessService.setPasswordByOldPassword(userSetPasswordDTO));
    }

    @Override
    public ResponseEntity<HttpStatus> setPassword(UserSetPasswordDTO userSetPasswordDTO) {
        return ResponseEntity.status(response(userBusinessService.setPasswordByOldPassword(userSetPasswordDTO)).getStatusCode()).build();
    }
}
