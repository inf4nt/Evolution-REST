package evolution.module.feed.rest.api;

import evolution.module.feed.dto.FeedDTO;
import evolution.module.feed.dto.FeedSaveDTO;
import evolution.module.feed.dto.FeedUpdateDTO;
import evolution.rest.AbstractRestService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedRestService extends AbstractRestService {

    ResponseEntity<FeedDTO> findOne(Long id);

    ResponseEntity<List<FeedDTO>> findAll();

    ResponseEntity<Page<FeedDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<FeedDTO> create(FeedSaveDTO feed);

    ResponseEntity<FeedDTO> update(FeedUpdateDTO feed);

    ResponseEntity<List<FeedDTO>> findMyFriendsFeed();

    ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<FeedDTO>> findFeedsForMe();

    ResponseEntity<Page<FeedDTO>> findFeedsForMe(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<FeedDTO>> findMyFriendsFeed(Long iam);

    ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<FeedDTO>> findFeedsForMe(Long iam);

    ResponseEntity<Page<FeedDTO>> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<FeedDTO> delete(Long id);
}
