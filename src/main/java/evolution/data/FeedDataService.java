package evolution.data;

import evolution.model.feed.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 24.09.2017.
 */
@Service
public class FeedDataService {

    private final FeedRepository feedRepository;

    @Autowired
    public FeedDataService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Transactional(readOnly = true)
    public Optional findOne(Long id) {
        return Optional.ofNullable(feedRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public List<Feed> findFeedsOfMyFriends(Long userId) {
        return feedRepository.findFeedsOfMyFriends(userId);
    }

    @Transactional(readOnly = true)
    public List<Feed> findMyFeeds(Long userId) {
        return feedRepository.findMyFeeds(userId);
    }

    @Transactional
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional
    public void delete(Long feedId, Long senderId) {
        Feed feed = feedRepository.findFeedByIdAndSenderId(feedId, senderId);
        feedRepository.delete(feed);
    }

    @Transactional
    public void deleteFeedMessage (Long feedId, Long toUserId) {
        Feed feed = feedRepository.findFeedByIdAndToUserId(feedId, toUserId);
        feedRepository.delete(feed);
    }

    @Transactional
    public Feed update(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

}
