package evolution.rest;

import evolution.common.UserRoleEnum;
import evolution.data.UserDataService;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    public UseRestServiceImpl(UserDataService userDataService, SecuritySupportService securitySupportService, PasswordEncoder passwordEncoder) {
        this.userDataService = userDataService;
        this.securitySupportService = securitySupportService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<List<User>> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        List<User> list;

        if (sort != null && !sort.isEmpty()) {
            sort = sort.toUpperCase();
        }

        if (page == null && size == null && sort != null && sortProperties != null && !sortProperties.isEmpty()) {

            list = userDataService.findAll(new Sort(Sort.Direction.valueOf(sort), sortProperties));

        } else if (page != null && size != null && sort != null && sortProperties != null && !sortProperties.isEmpty()) {

            list = userDataService.findAll(new PageRequest(page, size, new Sort(Sort.Direction.valueOf(sort), sortProperties))).getContent();

        } else if (page != null && size != null && sort == null) {

            list = userDataService.findAll(new PageRequest(page, size)).getContent();

        } else {
            list = userDataService.findAll();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<List<User>> findAllLoadLazy(Integer page, Integer size, String sort, List<String> sortProperties) {
        List<User> list;

        if (sort != null && !sort.isEmpty()) {
            sort = sort.toUpperCase();
        }

        if (page == null && size == null && sort != null && sortProperties != null && !sortProperties.isEmpty()) {

            list = userDataService.findAllLoadLazy(new Sort(Sort.Direction.valueOf(sort), sortProperties));

        } else if (page != null && size != null && sort != null && sortProperties != null && !sortProperties.isEmpty()) {

            list = userDataService.findAllLoadLazy(new PageRequest(page, size, new Sort(Sort.Direction.valueOf(sort), sortProperties)));

        } else {
            list = userDataService.findAllLoadLazy();
        }

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<User> findOneLoadLazy(Long id) {
        if (securitySupportService.isAllowed(id)) {
            Optional<User> optional = userDataService.findOneLoadLazy(id);
            if (optional.isPresent()) {
                return ResponseEntity.ok(optional.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Override
    public ResponseEntity<User> findOne(Long id) {
        Optional<User> optional = userDataService.findOne(id);
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
