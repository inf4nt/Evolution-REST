package evolution.crud.api;

import evolution.model.User;
import evolution.model.UserAdditionalData;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 07.11.2017.
 */
public interface UserCrudManagerService extends AbstractCrudManagerService<User, Long>, PageableManager, InitializeLazyCrudManagerService<User> {

    Optional<User> findByUsernameLazy(String username);

    Optional<User> findUserBySecretKey(String secretKey);

    Optional<UserAdditionalData> findUserAdditionalDataByUserId(Long userId);

    Optional<User> findOneAndIsActive(Long userId, boolean active);

    Optional<User> findOneAndIsBlock(Long userId, boolean block);

    Page<User> findAllAndIsActive(boolean active, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<User> findAllAndIsBlock(boolean block, Integer page, Integer size, String sort, List<String> sortProperties);

    List<User> findAllAndIsActive(boolean active, String sort, List<String> sortProperties);

    List<User> findAllAndIsBlock(boolean block, String sort, List<String> sortProperties);

    List<User> findAllAndIsActive(boolean active);

    List<User> findAllAndIsBlock(boolean block);

    Page<User> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties);

    Page<User> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    List<User> findAllLazy(String sort, List<String> sortProperties);

    List<User> findAll(String sort, List<String> sortProperties);

    List<User> findAllLazy();

    List<User> findAll();

    Optional<User> findUserBySecretKeyLazy(String secretKey);

    Optional<UserAdditionalData> findUserAdditionalDataByUserIdLazy(Long userId);

    Optional<User> findOneAndIsActiveLazy(Long userId, boolean active);

    Optional<User> findOneAndIsBlockLazy(Long userId, boolean block);

    Optional<User> findOneLazy(Long userId);

    CompletableFuture<Optional<User>> findOneAsync(Long userId);

    CompletableFuture<Optional<User>> findOneAsyncLazy(Long userId);

    Page<User> findAllAndIsActiveLazy(boolean active, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<User> findAllAndIsBlockLazy(boolean block, Integer page, Integer size, String sort, List<String> sortProperties);

    List<User> findAllAndIsActiveLazy(boolean active, String sort, List<String> sortProperties);

    List<User> findAllAndIsBlockLazy(boolean block, String sort, List<String> sortProperties);

    List<User> findAllAndIsActiveLazy(boolean active);

    List<User> findAllAndIsBlockLazy(boolean block);

    void delete(List<Long> ids);

    void deleteAll();

    void detach(User user);
}
