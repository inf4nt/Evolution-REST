package evolution.repository;

import evolution.common.RelationshipStatus;
import evolution.model.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 09.10.2017.
 */
public interface FriendRepository extends JpaRepository<Friend, Friend.FriendEmbeddable> {

    //-- получить подписчиков пользователя с ид = ???
    @Query("select f " +
            " from Friend f  " +
            " where f.status =:status " +
            " and f.actionUser.id <>:userId " +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    List<Friend> findFollowerByUser(@Param("userId") Long userId, @Param("status") RelationshipStatus pending);

    @Query("select f " +
            " from Friend f  " +
            " where f.status =:status " +
            " and f.actionUser.id <>:userId " +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    Page<Friend> findFollowerByUser(@Param("userId") Long userId, @Param("status") RelationshipStatus pending, Pageable pageable);

//-- получить заявки исходящие от пользователя с ид = ???
    //  или получить запросы в друзья исходящие от пользователя с ид = ???

    @Query("select f " +
            " from Friend f " +
            " where f.status =:status " +
            " and f.actionUser.id =:userId ")
    List<Friend> findRequestFromUser(@Param("userId") Long userId, @Param("status") RelationshipStatus pending);

    @Query("select f " +
            " from Friend f " +
            " where f.status =:status " +
            " and f.actionUser.id =:userId ")
    Page<Friend> findRequestFromUser(@Param("userId") Long userId, @Param("status") RelationshipStatus pending, Pageable pageable);

    //-- получить друзей пользователя с ид = ???
    @Query("select f" +
            " from Friend f" +
            " where f.status =:status" +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    List<Friend> findProgressByUser(@Param("userId") Long userId, @Param("status") RelationshipStatus accepted);

    @Query("select f" +
            " from Friend f" +
            " where f.status =:status" +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    Page<Friend> findProgressByUser(@Param("userId") Long userId, @Param("status") RelationshipStatus accepted, Pageable pageable);

    @Query("select f from Friend f " +
            " where f.pk.first.id =:first " +
            " and f.pk.second.id =:second ")
    Optional<Friend> findOneFriend(@Param("first") Long first, @Param("second") Long second);
}

