package evolution.data;


import evolution.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
@Service
public class UserDataService {

    private final UserRepository userRepository;

    @Autowired
    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public List<User> findAllLoadLazy() {
        return userRepository.findAllLoadLazy();
    }

    @Transactional(readOnly = true)
    public Page<User> findAllLoadLazy(Pageable page) {
        return userRepository.findAllLoadLazy(page);
    }

    @Transactional(readOnly = true)
    public List<User> findAllLoadLazy(Sort sort) {
        return userRepository.findAllLoadLazy(sort);
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneLoadLazy(Long id) {
        return Optional.ofNullable(userRepository.findOneLoadLazy(id));
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean exist(String username) {
        return userRepository.exist(username) != null;
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
    public void delete(List<User> userList) {
        userRepository.delete(userList);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneBySecretKeyLoadLazy(String secretKey) {
        User user = userRepository.findOneBySecretKey(secretKey);
        return Optional.ofNullable(user);
    }
}
