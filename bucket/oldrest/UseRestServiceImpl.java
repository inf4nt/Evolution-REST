package evolution.common.oldrest;

import evolution.common.UserRoleEnum;
import evolution.data.UserDataService;
import evolution.helper.HelperRestService;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Infant on 21.10.2017.
 */
@Service
public class UseRestServiceImpl implements UserRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDataService userDataService;

    private final SecuritySupportService securitySupportService;

    private final PasswordEncoder passwordEncoder;

    private final HelperDataService helperDataService;

    private final HelperRestService<User> helperRestService;

    @Autowired
    public UseRestServiceImpl(UserDataService userDataService, SecuritySupportService securitySupportService, PasswordEncoder passwordEncoder, HelperDataService helperDataService, HelperRestService<User> helperRestService) {
        this.userDataService = userDataService;
        this.securitySupportService = securitySupportService;
        this.passwordEncoder = passwordEncoder;
        this.helperDataService = helperDataService;
        this.helperRestService = helperRestService;
    }

    @Override
    public ResponseEntity<Page<User>> findAll(Integer page, Integer size, String sort, List<String> sortProperties, boolean lazy) {
        Pageable pageable = helperDataService.getPageableForUser(page, size, sort, sortProperties);
        Page<User> p;
        if (lazy) {
            p = userDataService.findAllLoadLazy(pageable);
        } else {
            p = userDataService.findAll(pageable);
        }
        return helperRestService.getResponseForPage(p);
    }

    @Override
    public ResponseEntity<User> findOne(Long id, boolean lazy) {
        Optional<User> optional;
        if (lazy) {
            optional = userDataService.findOneLoadLazy(id);
        } else {
            optional = userDataService.findOne(id);
        }

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> createNewUser(User user) {

        if (userDataService.exist(user.getUserAdditionalData().getUsername())) {
            return ResponseEntity.status(417).build();
        }

        logger.info(user.toString());

        String password = passwordEncoder.encode(user.getUserAdditionalData().getPassword());
        user.setRole(UserRoleEnum.USER);
        user.getUserAdditionalData().setRegistrationDate(new Date());
        user.getUserAdditionalData().setPassword(password);
//        user.getUserAdditionalData().setActive(false);
        user.getUserAdditionalData().setActive(true);
        // set secret key
        user.getUserAdditionalData().setSecretKey(UUID.randomUUID().toString());
        user.getUserAdditionalData().setBlock(false);

        logger.info(user.toString());

        try {
            userDataService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }

        //todo create letter. Registration successful

        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<HttpStatus> update(User user) {
        if (securitySupportService.isAllowed(user.getId())) {

            try {
                userDataService.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                return ResponseEntity.status(500).build();
            }
            return ResponseEntity.status(200).build();

        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        Optional<User> optional = userDataService.findOneLoadLazy(id);
        if (optional.isPresent()) {
            userDataService.delete(optional.get());
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(417).build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> block(Long id) {
        Optional<User> optional = userDataService.findOneLoadLazy(id);

        if (optional.isPresent()) {
            User user = optional.get();
            user.getUserAdditionalData().setBlock(true);
            userDataService.save(user);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> anBlock(Long id) {
        Optional<User> optional = userDataService.findOneLoadLazy(id);

        if (optional.isPresent()) {
            User user = optional.get();
            user.getUserAdditionalData().setBlock(false);
            userDataService.save(user);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> activated(String key) {
        Optional<User> optional = userDataService.findOneBySecretKeyLoadLazy(key);

        if (optional.isPresent()) {
            User user = optional.get();
            user.getUserAdditionalData().setActive(true);
            userDataService.save(user);
            return ResponseEntity.status(200).build();
        }

        //todo create letter. Activated successful

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> exist(String username) {
        if (userDataService.exist(username)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
