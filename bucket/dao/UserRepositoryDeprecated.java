package evolution.dao;

import evolution.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


interface UserRepositoryDeprecated extends JpaRepository<User, Long> {

//    User findUserByLogin(String login);
//
//    @Query("select new User(u.id, u.firstName, u.lastName) " +
//            " from User u where u.roleId = :role_id " +
//            " order by u.id desc ")
//    List<User> findAllByRole(@Param("role_id") Long roleId);
}

