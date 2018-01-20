package evolution.crud;

import evolution.crud.api.FeedCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.model.FeedSaveDTO;
import evolution.model.Feed;
import evolution.model.User;
import evolution.repository.FeedRepository;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Infant on 08.11.2017.
 */
@Service
public class FeedCrudManagerServiceImpl implements FeedCrudManagerService {

    @Value("${model.feed.maxfetch}")
    private Integer feedMaxFetch;

    @Value("${model.feed.defaultsort}")
    private String defaultFeedSortType;

    @Value("${model.feed.defaultsortproperties}")
    private String defaultFeedSortProperties;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserCrudManagerService userCrudManagerService;

    @Autowired
    private DateService dateService;

    @Override
    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

    @Override
    public List<Feed> findAll(String sort, List<String> sortProperties) {
        Sort s = getSortForRestService(sort, sortProperties,
                this.defaultFeedSortType, this.defaultFeedSortProperties);
        return feedRepository.findAll(s);
    }

    @Override
    public Page<Feed> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sort, sortProperties,
                this.feedMaxFetch, this.defaultFeedSortType, this.defaultFeedSortProperties);
        return feedRepository.findAll(p);
    }

    @Override
    public Optional<Feed> findOne(Long aLong) {
        return feedRepository.findOneFeed(aLong);
    }

    @Override
    public Feed save(Feed object) {
        return feedRepository.save(object);
    }

    @Override
    public void delete(Long aLong) {
        throw new UnsupportedOperationException("Please try  public boolean deleteById(Long id)");
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Feed> f = feedRepository.findOneFeed(id);
        if (!f.isPresent()) {
            return false;
        }
        feedRepository.delete(f.get());
        return true;
    }

    @Override
    public boolean deleteWithSender(Long id, Long senderId) {
        Optional<Feed> f = feedRepository.findFeedByIdAndSenderId(id, senderId);
        if (!f.isPresent()) {
            return false;
        }
        feedRepository.delete(f.get());
        return true;
    }

    @Override
    public boolean deleteWithToUser(Long id, Long toUserId) {
        Optional<Feed> f = feedRepository.findFeedByIdAndToUserId(id, toUserId);
        if (!f.isPresent()) {
            return false;
        }
        feedRepository.delete(f.get());
        return true;
    }

    @Override
    public List<Feed> findMyFriendsFeed(Long iam) {
        return feedRepository.findMyFriendsFeed(iam);
    }

    @Override
    public Page<Feed> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.feedMaxFetch, this.defaultFeedSortType, this.defaultFeedSortProperties);
        return feedRepository.findMyFriendsFeed(iam, p);
    }

    @Override
    public List<Feed> findFeedsForMe(Long iam) {
        return feedRepository.findFeedsForMe(iam);
    }

    @Override
    public Page<Feed> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.feedMaxFetch, this.defaultFeedSortType, this.defaultFeedSortProperties);
        return feedRepository.findFeedsForMe(iam, p);
    }

    @Override
    public Optional<Feed> findFeedByIdAndToUserId(Long feedId, Long toUserId) {
        return feedRepository.findFeedByIdAndToUserId(feedId, toUserId);
    }

    @Override
    public Optional<Feed> findFeedByIdAndSenderId(Long feedId, Long senderId) {
        return feedRepository.findFeedByIdAndSenderId(feedId, senderId);
    }

    @Override
    public List<Feed> findFeedBySender(Long sender) {
        return feedRepository.findFeedBySender(sender);
    }

    @Override
    public Page<Feed> findFeedBySender(Long sender, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.feedMaxFetch, this.defaultFeedSortType, this.defaultFeedSortProperties);
        return feedRepository.findFeedBySender(sender, p);
    }

    @Override
    @Transactional
    public void clearRowByUserForeignKey(Long id) {
        List<Feed> list = feedRepository.findAllFeedByToUserOrSender(id);
        feedRepository.delete(list);
    }

    @Override
    public CompletableFuture<List<Feed>> findFeedBySenderOrToUserAsync(Long userid) {
        return feedRepository.findAllFeedByToUserOrSenderAsync(userid);
    }

    @Override
    public List<Feed> findFeedBySenderOrToUser(Long userid) {
        return feedRepository.findAllFeedByToUserOrSender(userid);
    }

    @Override
    public void delete(List<Feed> list) {
        feedRepository.delete(list);
    }

    @Override
    @Transactional
    public Feed save(FeedSaveDTO feedSaveDTO) {

        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(feedSaveDTO.getSenderId());
        CompletableFuture<Optional<User>> ctu = userCrudManagerService.findOneAsync(feedSaveDTO.getToUserId());

        CompletableFuture.allOf(cs, ctu);

        Optional<User> sender = cs.join();
        Optional<User> toUser = ctu.join();

        if (!sender.isPresent() || !toUser.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            toUser.ifPresent(v -> userCrudManagerService.detach(v));
            return null;
        }

        Feed feed = new Feed();
        feed.setSender(sender.get());
        feed.setToUser(toUser.get());
        feed.setContent(feedSaveDTO.getContent());
        // todo set tags !
//        feed.setTags();
        feed.setDate(dateService.getCurrentDateInUTC());
        feed.setActive(true);

        return feedRepository.save(feed);
    }
}
