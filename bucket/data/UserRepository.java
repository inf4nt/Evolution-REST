package evolution.data;


import evolution.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 04.09.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(" select u " +
            "from User u " +
            "join fetch u.userAdditionalData uad " +
            "where uad.username =:username ")
    User findUserByUsername(@Param("username") String username);

    @Query("select u " +
            "from User u " +
            "join fetch u.userAdditionalData ")
    List<User> findAllLoadLazy();

    @Query(value = "select u " +
            "from User u " +
            "join fetch u.userAdditionalData ", countQuery = "select count(u) from User u")
    Page<User> findAllLoadLazy(Pageable pageable);

    @Query("select u " +
            "from User u " +
            "join fetch u.userAdditionalData ")
    List<User> findAllLoadLazy(Sort sort);

    @Deprecated
    @Query("select u " +
            "from User u " +
            "where u.id =:id")
    User findOne(@Param("id") Long id);

    @Query("select u " +
            "from User u " +
            "where u.id =:id")
    Optional<User> findOneUser(@Param("id") Long id);

    @Query("select u " +
            "from User u " +
            "join fetch u.userAdditionalData " +
            "where u.id =:id")
    User findOneLoadLazy(@Param("id") Long id);

    @Query(" select 1 " +
            "from User u " +
            "where u.userAdditionalData.username =:username ")
    Long exist(@Param("username") String username);

    @Query(" select u " +
            " from User u " +
            " join fetch u.userAdditionalData uad " +
            " where uad.secretKey =:secretKey ")
    User findOneBySecretKey(@Param("secretKey") String secretKey);
}
