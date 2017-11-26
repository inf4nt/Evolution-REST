package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.dto.model.FeedDTO;
import evolution.dto.model.FeedForSaveDTO;
import evolution.dto.model.FeedForUpdateDTO;
import evolution.model.Feed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
public interface FeedBusinessService {

    BusinessServiceExecuteResult delete(Long id);

    BusinessServiceExecuteResult deleteWithSender(Long id, Long senderId);

    BusinessServiceExecuteResult deleteWithToUser(Long id, Long toUserId);

    BusinessServiceExecuteResult<FeedDTO> update(FeedForUpdateDTO feed);

    BusinessServiceExecuteResult<FeedDTO> create(FeedForSaveDTO feed);

    List<FeedDTO> findAll();

    Page<FeedDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<FeedDTO> findMyFriendsFeed(Long iam);

    Page<FeedDTO> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<FeedDTO> findMyFriendsFeed();

    Page<FeedDTO> findMyFriendsFeed(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<FeedDTO> findFeedsForMe(Long iam);

    Page<FeedDTO> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<FeedDTO> findFeedsForMe();

    Page<FeedDTO> findFeedsForMe(Integer page, Integer size, String sortType, List<String> sortProperties);

    Optional<FeedDTO> findFeedByIdAndToUserId(Long feedId, Long toUserId);

    Optional<FeedDTO> findFeedByIdAndSenderId(Long feedId, Long senderId);

    Optional<FeedDTO> findFeedByIdAndSenderId(Long feedId);

    Optional<FeedDTO> findOne(Long id);

    BusinessServiceExecuteResult<List<FeedDTO>> findFeedBySender(Long sender);

    BusinessServiceExecuteResult<Page<FeedDTO>> findFeedBySender(Long sender, Integer page, Integer size, String sortType, List<String> sortProperties);
}
