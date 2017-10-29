package evolution.rest;

import evolution.data.UserDataService;
import evolution.helper.HelperDataService;
import evolution.helper.HelperRestService;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 29.10.2017.
 */
@Service
public class UserRestServiceImpl implements UserRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PasswordEncoder passwordEncoder;

    private final UserDataService userDataService;

    private final HelperDataService helperDataService;

    private final HelperRestService<User> helperRestService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public UserRestServiceImpl(UserDataService userDataService,
                               HelperDataService helperDataService,
                               HelperRestService<User> helperRestService,
                               PasswordEncoder passwordEncoder,
                               SecuritySupportService securitySupportService) {

        this.userDataService = userDataService;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
        this.passwordEncoder = passwordEncoder;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public ResponseEntity<Page<User>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable pageable = helperDataService.getPageableForUser(page, size, sortType, sortProperties);
        Page<User> p = userDataService.findAll(pageable);
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<User> findOne(Long userId) {
        Optional<User> optional = userDataService.findOne(userId);
        return helperRestService.getResponseForOptional(optional);
    }

    @Override
    public ResponseEntity<User> findByUsername(String username) {
        Optional<User> optional = userDataService.findUserByUsername(username);
        return helperRestService.getResponseForOptional(optional);
    }

    @Override
    public ResponseEntity<List<User>> findAll(String sortType, List<String> sortProperties) {
        Sort sort = helperDataService.getSortForUser(sortType, sortProperties);
        List<User> list = userDataService.findAll(sort);
        return helperRestService.getResponseForList(list);
    }

    @Override
    public ResponseEntity<HttpStatus> createNewUser(User user) {
        try {
            HttpStatus httpStatus = exist(user.getUserAdditionalData().getUsername()).getStatusCode();
            if (httpStatus == HttpStatus.OK) {
                return ResponseEntity.status(417).build();
            }

            logger.info(user.toString());
            String password = passwordEncoder.encode(user.getUserAdditionalData().getPassword());
            user.getUserAdditionalData().setPassword(password);

            //todo check valid user
            userDataService.save(user);

            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> updateAuthUser(User user) {
        if (!securitySupportService.isAllowed(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        return update(user);
    }

    @Override
    public ResponseEntity<HttpStatus> update(User user) {
        userDataService.save(user);
        return ResponseEntity.ok().build();
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
        if (userDataService.exist(username)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
