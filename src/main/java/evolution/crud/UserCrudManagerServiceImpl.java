package evolution.crud;

import evolution.crud.api.UserCrudManagerService;
import evolution.model.User;
import evolution.model.UserAdditionalData;
import evolution.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class UserCrudManagerServiceImpl implements UserCrudManagerService {

    private final UserRepository userRepository;

    @Autowired
    public UserCrudManagerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return userRepository.findAll(s);
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return userRepository.findAll(p);
    }

    @Override
    public Optional<User> findOne(Long aLong) {
        return userRepository.findOneUserById(aLong);
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(Long aLong) {
        Optional<User> optional = userRepository.findOneUserById(aLong);
        optional.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserBySecretKey(String secretKey) {
        return userRepository.findUserBySecretKey(secretKey);
    }

    @Override
    public Optional<UserAdditionalData> findUserAdditionalDataByUserId(Long userId) {
        Optional<User> optional = userRepository.findOneUserById(userId);
        if (optional.isPresent()) {
            Hibernate.initialize(optional.get().getUserAdditionalData());
            return Optional.of(optional.get().getUserAdditionalData());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findOneUserByIdAndIsActive(Long userId, boolean active) {
        return userRepository.findOneUserByIdAndIsActive(userId, active);
    }

    @Override
    public Optional<User> findOneUserByIdAndIsBlock(Long userId, boolean block) {
        return userRepository.findOneUserByIdAndIsBlock(userId, block);
    }

    @Override
    public Page<User> findUserAllByIsActive(boolean active, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return userRepository.findUserAllByIsActive(active, p);
    }

    @Override
    public Page<User> findUserAllByIsBlock(boolean block, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageable(page, size, sort, sortProperties);
        return userRepository.findUserAllByIsBlock(block, p);
    }

    @Override
    public List<User> findUserAllByIsActive(boolean active, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return userRepository.findUserAllByIsActive(active, s);
    }

    @Override
    public List<User> findUserAllByIsBlock(boolean block, String sort, List<String> sortProperties) {
        Sort s = getSort(sort, sortProperties);
        return userRepository.findUserAllByIsBlock(block, s);
    }

    @Override
    public List<User> findUserAllByIsActive(boolean active) {
        return userRepository.findUserAllByIsActive(active);
    }

    @Override
    public List<User> findUserAllByIsBlock(boolean block) {
        return userRepository.findUserAllByIsBlock(block);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Optional<User> optional = userRepository.findOneUserById(id);
            if (!optional.isPresent()) {
                return false;
            } else {
                userRepository.delete(optional.get());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Value("${model.user.maxfetch}")
    private Integer userMaxFetch;

    @Value("${model.user.defaultsort}")
    private String defaultUserSortType;

    @Value("${model.user.defaultsortproperties}")
    private String defaultUserSortProperties;

    @Override
    public Pageable getPageable(Integer page, Integer size, String sort, List<String> sortProperties) {
        return getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
    }

    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return getPageableForRestService(page, size,
                this.userMaxFetch);
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        return getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
    }
}