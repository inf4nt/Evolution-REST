package evolution.module.feed.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.module.feed.business.api.FeedBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.module.feed.dto.FeedDTO;
import evolution.module.feed.dto.FeedSaveDTO;
import evolution.module.feed.dto.FeedUpdateDTO;
import evolution.module.feed.rest.api.FeedRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedRestServiceImpl implements FeedRestService {

    private final FeedBusinessService feedBusinessService;

    @Autowired
    public FeedRestServiceImpl(FeedBusinessService feedBusinessService) {
        this.feedBusinessService = feedBusinessService;
    }

    @Override
    public ResponseEntity<FeedDTO> findOne(Long id) {
        Optional<FeedDTO> o = feedBusinessService.findOne(id);
        return response(o);
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findAll() {
        List<FeedDTO> list = feedBusinessService.findAll();
        return response(list);
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findAll(page, size, sortType, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<FeedDTO> create(FeedSaveDTO feed) {
        BusinessServiceExecuteResult<FeedDTO> b = feedBusinessService.create(feed);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).build();
        }
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.status(201).body(b.getResultObject());
        }

        return ResponseEntity.status(500).build();
    }

    @Override
    public ResponseEntity<FeedDTO> update(FeedUpdateDTO feed) {
        BusinessServiceExecuteResult<FeedDTO> b = feedBusinessService.update(feed);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        }
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).build();
        }
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return ResponseEntity.status(201).body(b.getResultObject());
        }

        return ResponseEntity.status(500).build();
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findMyFriendsFeed() {
        List<FeedDTO> list = feedBusinessService.findMyFriendsFeed();
        return response(list);
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findMyFriendsFeed(page, size, sortType, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findFeedsForMe() {
        List<FeedDTO> list = feedBusinessService.findFeedsForMe();
        return response(list);
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findFeedsForMe(page, size, sortType, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findMyFriendsFeed(Long iam) {
        List<FeedDTO> list = feedBusinessService.findMyFriendsFeed(iam);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findMyFriendsFeed(iam, page, size, sortType, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findFeedsForMe(Long iam) {
        List<FeedDTO> list = feedBusinessService.findFeedsForMe(iam);
        return response(list);
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findFeedsForMe(iam, page, size, sortType, sortProperties);
        return response(p);
    }

    @Override
    public ResponseEntity<FeedDTO> delete(Long id) {
        BusinessServiceExecuteResult b = feedBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).build();
        }

        return ResponseEntity.status(500).build();
    }
}
