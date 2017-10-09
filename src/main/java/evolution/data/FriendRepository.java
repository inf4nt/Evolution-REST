package evolution.data;

import evolution.common.FriendStatusEnum;
import evolution.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Infant on 09.10.2017.
 */
public interface FriendRepository extends JpaRepository<Friend, Friend.FriendEmbeddable> {

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
    Friend findFollowerByUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum requestStatus);

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
    Friend findRequestFromUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum requestStatus);

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
    Friend findProgressByUser(@Param("userId") Long userId, @Param("status") FriendStatusEnum progressStatus);

//-- проверить можно ли кинуть заявку на дружбу
//
//    SELECT *
//    FROM friends f
//    WHERE f.first_user_id = :first_id
//    AND f.second_user_id = :second_id;

    @Query("select 1 from Friend f " +
            " where f.pk.first.id =:first " +
            " and f.pk.second.id =:second ")
    Long isExist(Long first, Long second);

    @Query("select f from Friend f " +
            " where f.pk.first.id =:first " +
            " and f.pk.second.id =:second ")
    Friend findFriendsByUsers();

//-- проверить могу ли я подтвердить дружбу, если да вернуть строчку
//
//    SELECT *
//    FROM friends f
//    WHERE f.status = 'REQUEST'
//    AND f.action_user_id <> :accepted_user
//    AND f.first_user_id = :first_id
//    AND f.second_user_id = :second_id;

    @Query("select f " +
            " from Friend f " +
            " where f.status =:status " +
            " and f.actionUser.id =:action_user_id " +
            " and f.pk.first.id =:first_id " +
            " and f.pk.second.id =:second_id ")
    Friend findByAllParams(@Param("first_id") Long firstUserId, @Param("second_id") Long secondUserId,
                           @Param("action_user_id") Long actionUserId, @Param("status") FriendStatusEnum status);


}

