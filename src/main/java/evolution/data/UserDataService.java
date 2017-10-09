package evolution.data;



import evolution.model.User;
import evolution.service.UserTechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class UserDataService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserTechnicalService userTechnicalService;

    @Autowired
    public UserDataService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserTechnicalService userTechnicalService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTechnicalService = userTechnicalService;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findAllInitializeLazy() {
        return userRepository.findAllInitializeLazy();
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneInitializeLazy(Long id) {
        return Optional.ofNullable(userRepository.findOneInitializeLazy(id));
    }

    @Transactional
    public User save(User user) {
        if (user.getId() == null || user.getId().equals(0L)) {
            // throw new UnsupportedOperationException(" If you want create new user, use class UserTechnicalService, method createNewUser");
            User u = userTechnicalService.encodePassword(user);
            return userRepository.save(u);
        }

        return userRepository.save(user);
    }

    @Transactional
    public List<User> save(List<User> userList) {
        return userRepository.save(userList);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> optional = findOneInitializeLazy(id);
        optional.ifPresent(o -> delete(o));
    }

    @Transactional
    public void deleteListUserId(Collection<Long> collection) {
        List<User> list = userRepository.findAll(collection);
        delete(list);
    }

    @Transactional
    public void delete(List<User> userList) {
        userRepository.delete(userList);
    }
}
