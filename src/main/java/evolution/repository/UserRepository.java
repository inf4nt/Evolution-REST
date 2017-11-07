package evolution.repository;

import evolution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id =:id")
    Optional<User> findOneUserById(@Param("id") Long userId);

    @Query("select u " +
            " from User u " +
            " join fetch u.userAdditionalData as ua " +
            " where ua.username =:username ")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("select u " +
            " from User u " +
            " join fetch u.userAdditionalData as ua " +
            " where ua.secretKey =:key ")
    Optional<User> findUserBySecretKey(@Param("key") String secretKey);

    @Query(" select true " +
            "from User u " +
            "where u.userAdditionalData.username =:username ")
    Boolean exist(@Param("username") String username);
}
