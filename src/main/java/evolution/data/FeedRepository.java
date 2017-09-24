package evolution.data;

import evolution.model.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Infant on 26.07.2017.
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {

//    @Query(value = " select f " +
////            "new Feed (f.id, f.content, f.date, f.tags, s.id, s.firstName, s.lastName, tu.id, tu.firstName, tu.lastName ) " +
//            " from Friends fr " +
//            " join Feed f on f.sender.id = fr.friend.id and (f.toUser.id != :user_id or f.toUser.id is null)" +
//            " join f.sender as s " +
//            " left join f.toUser as tu " +
//            " where fr.user.id = :user_id" +
//            " order by f.date desc ")
//    List<Feed> findFeedsOfMyFriends(@Param("user_id") Long userId);
    @Query(value = " select * " +
            " from friends fr" +
            " join feed f on f.sender_id = fr.friend_id and (f.to_user_id != :user_id or f.to_user_id is null)" +
            " join user_data sender on f.sender_id = sender.id " +
            " join user_data tu on f.sender_id = tu.id " +
            " where fr.user_id = :user_id" +
            " order by f.date desc ", nativeQuery = true)
    List<Feed> findFeedsOfMyFriends(@Param("user_id") Long userId);

    @Query(" select f " +
//            " new Feed (f.id, f.content, f.date, f.tags, s.id, s.firstName, s.lastName, tu.id, tu.firstName, tu.lastName ) " +
            " from Feed f" +
            " join f.sender as s " +
            " left join f.toUser as tu " +
            " where (f.sender.id = :user_id and f.toUser is null) " +
            " or f.toUser.id = :user_id " +
            " order by f.date desc ")
    List<Feed> findMyFeeds(@Param("user_id") Long userId);

    @Transactional
    @Modifying
    @Query(" delete from Feed f" +
            " where f.id = :id and f.sender.id = :sender_id")
    void delete(@Param("id") Long feedId, @Param("sender_id") Long senderId);

    @Transactional
    @Modifying
    @Query(" delete from Feed f" +
            " where f.id = :id and f.toUser.id = :to_user_id")
    void deleteFeedMessage(@Param("id") Long feedId, @Param("to_user_id") Long toUserId);
}
