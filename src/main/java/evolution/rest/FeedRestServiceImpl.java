package evolution.rest;

import evolution.business.BusinessServiceExecuteResult;
import evolution.business.api.FeedBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.FeedDTO;
import evolution.dto.model.FeedForSaveDTO;
import evolution.dto.model.FeedForUpdateDTO;
import evolution.rest.api.FeedRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findAll() {
        List<FeedDTO> list = feedBusinessService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findAll(page, size, sortType, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<FeedDTO> create(FeedForSaveDTO feed) {
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
    public ResponseEntity<FeedDTO> update(FeedForUpdateDTO feed) {
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
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findMyFriendsFeed(page, size, sortType, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findFeedsForMe() {
        List<FeedDTO> list = feedBusinessService.findFeedsForMe();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<FeedDTO> p = feedBusinessService.findFeedsForMe(page, size, sortType, sortProperties);
        if (p.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findMyFriendsFeed(Long iam) {
        BusinessServiceExecuteResult<List<FeedDTO>> b = feedBusinessService.findMyFriendsFeed(iam);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && b.getResultObjectOptional().get().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && !b.getResultObjectOptional().get().isEmpty()) {
            return ResponseEntity.ok(b.getResultObject());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        BusinessServiceExecuteResult<Page<FeedDTO>> b = feedBusinessService.findMyFriendsFeed(iam, page, size, sortType, sortProperties);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && b.getResultObjectOptional().get().getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && !b.getResultObjectOptional().get().getContent().isEmpty()) {
            return ResponseEntity.ok(b.getResultObject());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<List<FeedDTO>> findFeedsForMe(Long iam) {
        BusinessServiceExecuteResult<List<FeedDTO>> b = feedBusinessService.findFeedsForMe(iam);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && b.getResultObjectOptional().get().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && !b.getResultObjectOptional().get().isEmpty()) {
            return ResponseEntity.ok(b.getResultObject());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<Page<FeedDTO>> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        BusinessServiceExecuteResult<Page<FeedDTO>> b = feedBusinessService.findFeedsForMe(iam, page, size, sortType, sortProperties);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.FORBIDDEN) {
            return ResponseEntity.status(403).build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && b.getResultObjectOptional().get().getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent() && !b.getResultObjectOptional().get().getContent().isEmpty()) {
            return ResponseEntity.ok(b.getResultObject());
        }

        return ResponseEntity.status(417).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        BusinessServiceExecuteResult b = feedBusinessService.delete(id);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK) {
            return ResponseEntity.ok().build();
        } else if (b.getExecuteStatus() == BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE) {
            return ResponseEntity.status(417).build();
        }

        return ResponseEntity.status(500).build();
    }
}
