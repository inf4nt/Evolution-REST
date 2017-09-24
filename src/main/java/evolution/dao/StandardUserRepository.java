package evolution.dao;

import evolution.model.user.UserLight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Infant on 05.07.2017.
 */
interface StandardUserRepository extends JpaRepository<UserLight, Long> {

    @Query(" select u from UserLight u where u.id = :id")
    UserLight selectIdFirstLastName(@Param("id") Long id);

    @Query(" select u from UserLight u " +
            " where (lower(u.firstName) like lower (concat('%', :p1, '%')) and lower(u.lastName) like lower(concat('%', :p2, '%'))) " +
            " or (lower(u.lastName) like lower (concat('%', :p1, '%')) and lower(u.firstName) like lower(concat('%', :p2, '%')))")
    List<UserLight> findUserByFirstLastName(@Param("p1") String p1, @Param("p2") String p2, Pageable pageable);

    @Query(" select u from UserLight u " +
            " where (lower(u.firstName) like lower (concat('%', :p1, '%'))) or (lower(u.lastName) like lower(concat('%', :p1, '%')))")
    List<UserLight> findUserByFirstOrLastName(@Param("p1") String p1, Pageable pageable);

    @Query(" select u from UserLight u ")
    List<UserLight> findUsers(Pageable pageable);


}
