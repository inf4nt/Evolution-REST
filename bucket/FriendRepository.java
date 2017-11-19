//package evolution.data;
//
//
//import evolution.model.friend.Friends;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
///**
// * Created by Infant on 08.08.2017.
// */
//interface FriendRepository extends JpaRepository<Friends, Long> {
//
//    @Query(" select f " +
//            " from Friends f " +
//            " join fetch f.friend " +
//            " join fetch f.user ")
//    List<Friends> findAll();
//
//    @Query(" select f " +
//            " from Friends f " +
//            " join fetch f.user " +
//            " join fetch f.friend " +
//            " where f.user.id =:userId " +
//            " and f.status =:status ")
//    List<Friends> findFriendsByStatusAndUser(@Param("userId") Long userId, @Param("status") Long status);
//
//    @Query(" select f " +
//            " from Friends f " +
//            " join fetch f.user " +
//            " join fetch f.friend " +
//            " where f.user.id =:authUserId " +
//            " and f.friend.id =:friendUserId " +
//            " and f.status =:status ")
//    Friends getFriendsByUserIdAndStatus(@Param("authUserId") Long authUserId, @Param("friendUserId") Long friendUserId, @Param("status") Long status);
//
////    @Query("select 1 " +
////            " from Friends f " +
////            " where (f.user.id =:first and f.friend.id =:second) " +
////            " or (f.user.id =:second and f.friend.id =:first) ")
////    List existFriend(@Param("first") Long first, @Param("second") Long second);
//
//
//    @Query(value = "select 1 from friends " +
//            "WHERE (user_id = :first and friend_id = :second) " +
//            "or (user_id = :second and friend_id = :first) for UPDATE NOWAIT ", nativeQuery = true)
//    List existFriend(@Param("first") Long first, @Param("second") Long second);
//
//    @Query(value = " select f.status " +
//            " from Friends f " +
//            " where f.user.id =:first " +
//            " and f.friend.id =:second ")
//    Long findFriendStatusByUsers(@Param("first") Long first, @Param("second") Long second);
//
//
////    //todo: in future repair this
////    @Query(" FROM Friends f, StandardUser u " +
////            " where u.id = f.friend.id and f.user.id = :authUserId" +
////            " and u.id = :id ")
////    Friends findUserAndFriendStatus(@Param("authUserId") Long authUserId, @Param("id") Long id);
////
////
////    @Query("select count(f) from Friends f where f.user.id = :user_id and f.status = :status_id")
////    Long countFriendsByStatus(@Param("user_id") Long userId, @Param("status_id") Long statusId);
////
////    @Query(" select f " +
////            " from  Friends f " +
////            " where f.user.id = :user_id " +
////            " and f.status = :status_id "  +
////            " order by rand() ")
////    List<User> randomFriends(@Param("user_id") Long userId, @Param("status_id") Long statusId, Pageable pageable);
//}
