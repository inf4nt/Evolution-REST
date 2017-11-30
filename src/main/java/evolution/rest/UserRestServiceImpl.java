package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.UserBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserForSaveDTO;
import evolution.dto.model.UserForUpdateDTO;
import evolution.dto.model.UserFullDTO;
import evolution.rest.api.UserRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class UserRestServiceImpl implements UserRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBusinessService userBusinessService;

    @Override
    public ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<UserDTO> p = userBusinessService.findAll(page, size, sortType, sortProperties);

        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<UserDTO> findOne(Long userId) {
        return userBusinessService.findOne(userId)
                .map(o -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserFullDTO> findOneFull(Long userId) {
        return userBusinessService
                .findOneUserFull(userId)
                .map(o -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserDTO> findByUsername(String username) {
        return userBusinessService
                .findByUsername(username)
                .map(o -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserFullDTO> findByUsernameFull(String username) {
        return userBusinessService
                .findByUsernameFull(username)
                .map(o -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties) {
        List<UserDTO> list = userBusinessService.findAll(sortType, sortProperties);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<UserFullDTO>> findAllFull(String sortType, List<String> sortProperties) {
        List<UserFullDTO> list = userBusinessService.findAllFull(sortType, sortProperties);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<UserFullDTO>> findAllFullPage(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public ResponseEntity<UserFullDTO> createNewUser(UserForSaveDTO user) {
        BusinessServiceExecuteResult<UserFullDTO> b = userBusinessService.createNewUser(user);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.status(201).body(b.getResultObjectOptional().get());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.USER_IS_ALREADY_EXIST_REGISTRATION_FAILED) {
            return ResponseEntity.status(417).build();
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity update(UserForUpdateDTO user) {
        BusinessServiceExecuteResult b = userBusinessService.update(user);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.ok().body(b.getExecuteStatus());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).body(b.getExecuteStatus());
        }
        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity block(Long id) {
        return null;
    }

    @Override
    public ResponseEntity anBlock(Long id) {
        return null;
    }

    @Override
    public ResponseEntity activated(String key) {
        return null;
    }

    @Override
    public ResponseEntity exist(String username) {
        Optional<UserDTO> optional = userBusinessService.findByUsername(username);
        if (optional.isPresent()) {
            return ResponseEntity.ok().body("User by username, " + username + ", exist");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User by username, " + username + ", not found!");
        }
    }

    @Override
    public ResponseEntity<UserFullDTO> setPassword(UserFullDTO userFullDTO) {
        BusinessServiceExecuteResult<UserFullDTO> b = userBusinessService.setPasswordByOldPassword(userFullDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.ok(b.getResultObject());
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(417).build();
    }
}
