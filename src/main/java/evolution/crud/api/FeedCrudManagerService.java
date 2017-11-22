package evolution.crud.api;

import evolution.model.Feed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FeedCrudManagerService extends AbstractCrudManagerService<Feed, Long>, PageableManager {

    boolean deleteById(Long id);

    boolean deleteWithSender(Long id, Long senderId);

    boolean deleteWithToUser(Long id, Long toUserId);

    List<Feed> findMyFriendsFeed(Long iam);

    Page<Feed> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<Feed> findFeedsForMe(Long iam);

    Page<Feed> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    Optional<Feed> findFeedByIdAndToUserId(Long feedId, Long toUserId);

    Optional<Feed> findFeedByIdAndSenderId(Long feedId, Long senderId);
}
