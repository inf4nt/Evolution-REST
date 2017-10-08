//package evolution.data;
//
//import evolution.model.Feed;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
///**
// * Created by Infant on 26.07.2017.
// */
//interface FeedRepository extends JpaRepository<Feed, Long> {
//
//    @Query(value = " select f from Feed f " +
//            " left join fetch f.toUser " +
//            " join fetch f.sender ")
//    List<Feed> findAll();
//
//    @Query(value = " select * " +
//            " from friends fr" +
//            " join feed f on f.sender_id = fr.friend_id and (f.to_user_id != :user_id or f.to_user_id is null)" +
//            " join user_data sender on f.sender_id = sender.id " +
//            " join user_data tu on f.sender_id = tu.id " +
//            " where fr.user_id = :user_id" +
//            " order by f.date desc ", nativeQuery = true)
//    List<Feed> findFeedsOfMyFriends(@Param("user_id") Long userId);
//
//    @Query(" select f " +
//            " from Feed f" +
//            " join fetch f.sender as s " +
//            " left join fetch f.toUser as tu " +
//            " where (f.sender.id = :user_id and f.toUser is null) " +
//            " or f.toUser.id = :user_id " +
//            " order by f.date desc ")
//    List<Feed> findMyFeeds(@Param("user_id") Long userId);
//
//    @Query("select f from Feed f " +
//            " where f.id = :id and f.toUser.id = :toUserId")
//    Feed findFeedByIdAndToUserId(@Param("id") Long id, @Param("toUserId") Long toUserId);
//
//    @Query("select f from Feed f " +
//            " where f.id = :id and f.sender.id = :senderId")
//    Feed findFeedByIdAndSenderId(@Param("id") Long id, @Param("senderId") Long senderId);
//}
