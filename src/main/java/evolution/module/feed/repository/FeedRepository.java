package evolution.module.feed.repository;

import evolution.module.feed.model.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("select f from Feed f where f.id =:id")
    Optional<Feed> findOneFeed(@Param("id") Long id);

    @Query("select f from Friend fr, Feed f " +
            "where (  (f.sender.id = fr.pk.first.id and fr.pk.second.id =:id) or (f.sender.id = fr.pk.second.id and fr.pk.first.id =:id) ) " +
            " or (  (f.toUser.id = fr.pk.first.id and fr.pk.second.id =:id and f.sender <>:id ) or (f.toUser.id = fr.pk.second.id and fr.pk.first.id =:id and f.sender <>:id ) )")
    List<Feed> findMyFriendsFeed(@Param("id") Long userId);

    @Query("select f from Friend fr, Feed f " +
            "where (  (f.sender.id = fr.pk.first.id and fr.pk.second.id =:id) or (f.sender.id = fr.pk.second.id and fr.pk.first.id =:id) ) " +
            " or (  (f.toUser.id = fr.pk.first.id and fr.pk.second.id =:id and f.sender <>:id ) or (f.toUser.id = fr.pk.second.id and fr.pk.first.id =:id and f.sender <>:id ) )")
    Page<Feed> findMyFriendsFeed(@Param("id") Long userId, Pageable pageable);

    @Query("select f " +
            "from Feed f " +
            "where (f.sender.id =:id and f.toUser is null) " +
            "or (f.toUser.id =:id)")
    List<Feed> findFeedsForMe(@Param("id") Long iam);

    @Query("select f " +
            "from Feed f " +
            "where (f.sender.id =:id and f.toUser is null) " +
            "or (f.toUser.id =:id)")
    Page<Feed> findFeedsForMe(@Param("id") Long iam, Pageable pageable);

    @Query("select f from Feed f " +
            " where f.id = :id and f.toUser.id = :toUserId")
    Optional<Feed> findFeedByIdAndToUserId(@Param("id") Long id, @Param("toUserId") Long toUserId);

    @Query("select f from Feed f " +
            " where f.id = :id and f.sender.id = :senderId")
    Optional<Feed> findFeedByIdAndSenderId(@Param("id") Long id, @Param("senderId") Long senderId);

    @Query("select f from Feed f where f.sender.id =:s")
    List<Feed> findFeedBySender(@Param("s") Long sender);

    @Query("select f from Feed f where f.sender.id =:s")
    Page<Feed> findFeedBySender(@Param("s") Long sender, Pageable pageable);

    @Query("select f from Feed f where f.sender.id =:id or f.toUser.id =:id")
    List<Feed> findAllFeedByToUserOrSender(@Param("id") Long id);
}
