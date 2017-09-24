package evolution.dao;

import evolution.model.feed.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Infant on 26.07.2017.
 */
@Service
public class FeedServiceDao {

    @Autowired
    private FeedRepository feedRepository;

    @PersistenceContext
    private EntityManager entityManager;

//    @Transactional
    public List<Feed> findFeedsOfMyFriends(Long userId) {
//        Query query = entityManager.createQuery(" select f " +
//                " from Friends fr " +
//                " join Feed f on f.sender.id = fr.friend.id and (f.toUser.id != :user_id or f.toUser.id is null)" +
//                " join f.sender as s " +
//                " left join f.toUser as tu " +
//                " where fr.user.id = :user_id" +
//                " order by f.date desc ");
//        query.setParameter("user_id", userId);
//        return query.getResultList();
        return feedRepository.findFeedsOfMyFriends(userId);
    }

    @Transactional
    public List<Feed> findMyFeeds(Long userId) {
        return feedRepository.findMyFeeds(userId);
    }

    @Transactional
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional
    public void delete(Long feedId, Long senderId) {
        feedRepository.delete(feedId, senderId);
    }

    @Transactional
    public void deleteFeedMessage (Long feedId, Long toUserId) {
        feedRepository.deleteFeedMessage(feedId, toUserId);
    }

    @Transactional
    public Feed update(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional
    public List<Feed> findAll() {
        return feedRepository.findAll();
    }
}
