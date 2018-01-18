package evolution.crud;

import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.FeedCrudManagerService;
import evolution.crud.api.FriendCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.model.User;
import evolution.model.UserAdditionalData;
import evolution.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class UserCrudManagerServiceImpl implements UserCrudManagerService {

    private final UserRepository userRepository;

    @Value("${model.user.maxfetch}")
    private Integer userMaxFetch;

    @Value("${model.user.defaultsort}")
    private String defaultUserSortType;

    @Value("${model.user.defaultsortproperties}")
    private String defaultUserSortProperties;

    @PersistenceContext
    private EntityManager entityManager;

    private final FriendCrudManagerService friendCrudManagerService;

    private final FeedCrudManagerService feedCrudManagerService;

    private final DialogCrudManagerService dialogCrudManagerService;

    @Autowired
    public UserCrudManagerServiceImpl(UserRepository userRepository,
                                      @Lazy FriendCrudManagerService friendCrudManagerService,
                                      FeedCrudManagerService feedCrudManagerService,
                                      DialogCrudManagerService dialogCrudManagerService) {
        this.userRepository = userRepository;
        this.friendCrudManagerService = friendCrudManagerService;
        this.feedCrudManagerService = feedCrudManagerService;
        this.dialogCrudManagerService = dialogCrudManagerService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserBySecretKeyLazy(String secretKey) {
        Optional<User> op = userRepository.findUserBySecretKey(secretKey);
        op.ifPresent(this::initializeLazy);
        return op;
    }

    @Override
    public Optional<UserAdditionalData> findUserAdditionalDataByUserIdLazy(Long userId) {
        return userRepository.findOneUserById(userId).map(o -> o.getUserAdditionalData());
    }

    @Override
    public Optional<User> findOneAndIsActiveLazy(Long userId, boolean active) {
        Optional<User> op = userRepository.findOneUserByIdAndIsActive(userId, active);
        op.ifPresent(this::initializeLazy);
        return op;
    }

    @Override
    public Optional<User> findOneAndIsBlockLazy(Long userId, boolean block) {
        return userRepository.findOneUserByIdAndIsBlock(userId, block);
    }

    @Override
    public Optional<User> findOneLazy(Long userId) {
        Optional<User> op = userRepository.findOneUserById(userId);
        op.ifPresent(this::initializeLazy);
        return op;
    }

    @Override
    public CompletableFuture<Optional<User>> findOneAsync(Long userId) {
        return userRepository.findOneUserByIdAsync(userId)
                .thenApply(v -> Optional.ofNullable(v));
    }

    @Override
    public CompletableFuture<Optional<User>> findOneAsyncLazy(Long userId) {
        return userRepository.findOneUserByIdAsync(userId)
                .thenApply(v -> {
                    if (v != null) {
                        return Optional.of(initializeLazy(v));
                    } else {
                        return Optional.empty();
                    }
                });
    }

    @Override
    public Page<User> findAllAndIsActiveLazy(boolean active, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsActiveLazy(active, p);
    }

    @Override
    public Page<User> findAllAndIsBlockLazy(boolean block, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsBlock(block, p);
    }

    @Override
    public List<User> findAllAndIsActiveLazy(boolean active, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsActive(active, s);
    }

    @Override
    public List<User> findAllAndIsBlockLazy(boolean block, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsBlockLazy(block, s);
    }

    @Override
    public List<User> findAllAndIsActiveLazy(boolean active) {
        return userRepository.findUserAllByIsActiveLazy(active);
    }

    @Override
    public List<User> findAllAndIsBlockLazy(boolean block) {
        return userRepository.findUserAllByIsBlockLazy(block);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(o -> delete(o));
    }

    @Override
    public List<User> findAll(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findAll(s);
    }

    @Override
    public List<User> findAllLazy() {
        return userRepository.findAllFetchLazy();
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findAll(p);
    }

    @Override
    public List<User> findAllLazy(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findAllFetchLazy(s);
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
    @Transactional
    public void delete(Long aLong) {
        Optional<User> o = userRepository.findOneUserById(aLong);
        o.ifPresent(op -> Hibernate.initialize(op.getUserAdditionalData()));
        if (o.isPresent()) {
            feedCrudManagerService.clearRowByUserForeignKey(o.get().getId());
            friendCrudManagerService.clearRowByUserForeignKey(o.get().getId());
            dialogCrudManagerService.clearRowByUserForeignKey(o.get().getId());
            //todo clear channel message

            userRepository.delete(o.get());
        }
    }

    @Override
    public void deleteAll() {
        List<User> list = userRepository.findAllFetchLazy();
        userRepository.delete(list);
    }

    @Override
    @Transactional
    public Optional<User> findByUsernameLazy(String username) {
        Optional<User> op = userRepository.findUserByUsername(username);
        op.ifPresent(this::initializeLazy);
        return op;
    }

    @Override
    public Optional<User> findUserBySecretKey(String secretKey) {
        return userRepository.findUserBySecretKey(secretKey);
    }

    @Override
    @Transactional
    public Optional<UserAdditionalData> findUserAdditionalDataByUserId(Long userId) {
        Optional<User> optional = userRepository.findOneUserById(userId);
        if (optional.isPresent()) {
            Hibernate.initialize(optional.get().getUserAdditionalData());
            return Optional.of(optional.get().getUserAdditionalData());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findOneAndIsActive(Long userId, boolean active) {
        return userRepository.findOneUserByIdAndIsActive(userId, active);
    }

    @Override
    public Optional<User> findOneAndIsBlock(Long userId, boolean block) {
        return userRepository.findOneUserByIdAndIsBlock(userId, block);
    }

    @Override
    public Page<User> findAllAndIsActive(boolean active, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsActive(active, p);
    }

    @Override
    public Page<User> findAllAndIsBlock(boolean block, Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsBlock(block, p);
    }

    @Override
    public List<User> findAllAndIsActive(boolean active, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsActive(active, s);
    }

    @Override
    public List<User> findAllAndIsBlock(boolean block, String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findUserAllByIsBlock(block, s);
    }

    @Override
    public List<User> findAllAndIsActive(boolean active) {
        return userRepository.findUserAllByIsActive(active);
    }

    @Override
    public List<User> findAllAndIsBlock(boolean block) {
        return userRepository.findUserAllByIsBlock(block);
    }

    @Override
    public Page<User> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.userMaxFetch, this.defaultUserSortType, this.defaultUserSortProperties);
        return userRepository.findAllFetchLazy(p);
    }

    @Override
    public void detach(User user) {
        entityManager.detach(user);
    }

    @Override
    @Transactional
    public User initializeLazy(User object) {
        Hibernate.initialize(object.getUserAdditionalData());
        return object;
    }
}
