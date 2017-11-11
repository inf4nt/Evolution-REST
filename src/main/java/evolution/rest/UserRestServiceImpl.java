package evolution.rest;

import evolution.business.api.UserBusinessService;
import evolution.dto.model.UserDTO;
import evolution.dto.model.UserDTOForSave;
import evolution.dto.model.UserForUpdate;
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
//        Pageable pageable = helperDataService.getPageableForUser(page, size, sortType, sortProperties);
//        Page<User> p = userDataService.findAll(pageable);
//        return helperRestService.getResponseForPage(p);

        Page<UserDTO> p = userBusinessService.findAll(page, size, sortType, sortProperties);

        if(p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<UserDTO> findOne(Long userId) {
        return userBusinessService
                .findOne(userId)
                .map(o  -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserFullDTO> findOneFull(Long userId) {
        return userBusinessService
                .findOneUserFull(userId)
                .map(o  -> ResponseEntity.ok(o))
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserDTO> findByUsername(String username) {
        return userBusinessService
                .findByUsername(username)
                .map(o  -> ResponseEntity.ok(o))
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
        List<UserDTO> list =  userBusinessService.findAll(sortType, sortProperties);
        if(list.isEmpty()) {
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
    public ResponseEntity<HttpStatus> createNewUser(UserDTOForSave user) {
//        try {
//            HttpStatus httpStatus = exist(user.getUserAdditionalData().getUsername()).getStatusCode();
//            if (httpStatus == HttpStatus.OK) {
//                return ResponseEntity.status(417).build();
//            }
//
//            logger.info(user.toString());
//            String password = passwordEncoder.encode(user.getUserAdditionalData().getPassword());
//            user.getUserAdditionalData().setPassword(password);
//
//            //todo check valid user
//            userDataService.save(user);
//
//            return ResponseEntity.status(201).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(500).build();
//        }
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> update(UserForUpdate user) {
//        userDataService.save(user);
//        return ResponseEntity.ok().build();
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> block(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> anBlock(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> activated(String key) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> exist(String username) {
//        if (userDataService.exist(username)) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.noContent().build();
        return null;
    }
}
