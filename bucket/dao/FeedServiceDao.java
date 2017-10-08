package evolution.dao;

import evolution.data.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Infant on 26.07.2017.
 */
@Service
@Deprecated
public class FeedServiceDao {

    private final FeedRepository feedRepository;

    @Autowired
    public FeedServiceDao(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Transactional
    public List<Feed> findFeedsOfMyFriends(Long userId) {
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
//        feedRepository.delete(feedId, senderId);
    }

    @Transactional
    public void deleteFeedMessage (Long feedId, Long toUserId) {
//        feedRepository.deleteFeedMessage(feedId, toUserId);
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
