package evolution.crud.api;

import evolution.model.User;
import evolution.model.UserAdditionalData;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface UserCrudManagerService extends AbstractCrudManagerService<User, Long>, PageableManager {

    Optional<User> findByUsername(String username);

    Optional<User> findUserBySecretKey(String secretKey);

    Optional<UserAdditionalData> findUserAdditionalDataByUserId(Long userId);

    Optional<User> findOneUserByIdAndIsActive(Long userId, boolean active);

    Optional<User> findOneUserByIdAndIsBlock(Long userId, boolean block);

    Page<User> findUserAllByIsActive(boolean active, Integer page, Integer size, String sort, List<String> sortProperties);

    Page<User> findUserAllByIsBlock(boolean block, Integer page, Integer size, String sort, List<String> sortProperties);

    List<User> findUserAllByIsActive(boolean active, String sort, List<String> sortProperties);

    List<User> findUserAllByIsBlock(boolean block, String sort, List<String> sortProperties);

    List<User> findUserAllByIsActive(boolean active);

    List<User> findUserAllByIsBlock(boolean block);

    boolean deleteById(Long id);
}
