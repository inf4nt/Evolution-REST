package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Infant on 09.10.2017.
 */
interface FriendRepository extends JpaRepository<Friend, Friend.FriendEmbeddable> {

//-- получить подписчиков пользователя с ид = ???
//
//    SELECT *
//    FROM friends f
//    WHERE f.status = 'REQUEST'
//    AND f.action_user_id <> :user_id
//    AND (f.first_user_id = :user_id OR f.second_user_id = :user_id);

    @Query("select f " +
            " from Friend f  " +
            " where f.status =:status " +
            " and f.actionUser.id <>:userId " +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    List<Friend> findFollowerByUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum requestStatus);

//-- получить заявки исходящие от пользователя с ид = ???
//
//    SELECT *
//    FROM friends f
//    WHERE f.status = 'REQUEST'
//    AND f.action_user_id = :user_id;

    @Query("select f " +
            " from Friend f " +
            " where f.status =:status " +
            " and f.actionUser.id =:userId ")
    List<Friend> findRequestFromUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum requestStatus);

//-- получить друзей пользователя с ид = ???
//
//    SELECT *
//    FROM friends f
//    WHERE f.status = 'PROGRESS'
//    AND (f.first_user_id = :user_id OR f.second_user_id = :user_id);

    @Query("select f" +
            " from Friend f" +
            " where f.status =:status" +
            " and (f.pk.first.id =:userId or f.pk.second.id =:userId)")
    List<Friend> findProgressByUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum progressStatus);


    @Query("select f from Friend f " +
            " where f.pk.first.id =:first " +
            " and f.pk.second.id =:second ")
    Friend findOne(@Param("first") Long first, @Param("second") Long second);
}

