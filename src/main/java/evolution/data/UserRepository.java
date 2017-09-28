package evolution.data;

import evolution.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Infant on 04.09.2017.
 */
interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u " +
            "from User u " +
            "where u.username =:username")
    User findUserByUsername(@Param("username") String username);

}
