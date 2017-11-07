package evolution.rest;

import evolution.dto.model.UserDTO;
import evolution.rest.api.UserRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @Override
    public ResponseEntity<Page<UserDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
//        Pageable pageable = helperDataService.getPageableForUser(page, size, sortType, sortProperties);
//        Page<User> p = userDataService.findAll(pageable);
//        return helperRestService.getResponseForPage(p);
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> findOne(Long userId) {
//        Optional<User> optional = userDataService.findOne(userId);
//        return helperRestService.getResponseForOptional(optional);
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> findByUsername(String username) {
//        Optional<User> optional = userDataService.findUserByUsername(username);
//        return helperRestService.getResponseForOptional(optional);
        return null;
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll(String sortType, List<String> sortProperties) {
//        Sort sort = helperDataService.getSortForUser(sortType, sortProperties);
//        List<User> list = userDataService.findAll(sort);
//        return helperRestService.getResponseForList(list);
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> createNewUser(UserDTO user) {
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
    public ResponseEntity<HttpStatus> updateAuthUser(UserDTO user) {
//        if (!securitySupportService.isAllowed(user.getId())) {
//            return ResponseEntity.status(403).build();
//        }
//        return update(user);
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> update(UserDTO user) {
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
