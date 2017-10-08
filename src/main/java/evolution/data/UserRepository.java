package evolution.data;


import evolution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Infant on 04.09.2017.
 */
interface UserRepository extends JpaRepository<User, Long> {

    @Query(" select u " +
            "from User u " +
            "join fetch u.userAdditionalData uad " +
            "where uad.username =:username ")
    User findUserByUsername(@Param("username") String username);

    @Query("select u " +
            "from User u " +
            "join fetch u.userAdditionalData ")
    List<User> findAllInitializeLazy();

    @Query("select u from User u where u.id =:id")
    User findOne(@Param("id") Long id);

    @Query("select u from User u " +
            "join fetch u.userAdditionalData " +
            "where u.id =:id")
    User findOneInitializeLazy(@Param("id") Long id);
}
