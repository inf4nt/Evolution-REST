package evolution.business;

import evolution.business.api.FeedBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.crud.api.FeedCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.FeedDTOTransfer;
import evolution.dto.modelOld.FeedDTO;
import evolution.dto.modelOld.FeedForSaveDTO;
import evolution.dto.modelOld.FeedForUpdateDTO;
import evolution.model.Feed;
import evolution.model.User;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedBusinessServiceImpl implements FeedBusinessService {

    private final FeedCrudManagerService feedCrudManagerService;

    private final SecuritySupportService securitySupportService;

    private final UserCrudManagerService userCrudManagerService;

    private final FeedDTOTransfer feedDTOTransfer;

    private final DateService dateService;

    @Autowired
    public FeedBusinessServiceImpl(FeedCrudManagerService feedCrudManagerService,
                                   SecuritySupportService securitySupportService,
                                   UserCrudManagerService userCrudManagerService,
                                   FeedDTOTransfer feedDTOTransfer,
                                   DateService dateService) {
        this.feedCrudManagerService = feedCrudManagerService;
        this.securitySupportService = securitySupportService;
        this.userCrudManagerService = userCrudManagerService;
        this.feedDTOTransfer = feedDTOTransfer;
        this.dateService = dateService;
    }


    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        boolean r;
        if (securitySupportService.isAdmin()) {
            r = feedCrudManagerService.deleteById(id);
        } else {
            Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
            r = feedCrudManagerService.deleteWithSender(id, auth);
        }
        if (r) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
    }

    @Override
    public BusinessServiceExecuteResult deleteWithSender(Long id, Long senderId) {
        if (!securitySupportService.isAllowedFull(senderId)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        boolean r = feedCrudManagerService.deleteWithSender(id, senderId);
        if (r) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
    }

    @Override
    public BusinessServiceExecuteResult deleteWithToUser(Long id, Long toUserId) {
        if (!securitySupportService.isAllowedFull(toUserId)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        boolean r = feedCrudManagerService.deleteWithSender(id, toUserId);
        if (r) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
    }

    @Override
    public BusinessServiceExecuteResult<FeedDTO> update(FeedForUpdateDTO feed) {
        if (!securitySupportService.isAllowedFull(feed.getSenderId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Feed> originalOptional = feedCrudManagerService.findOne(feed.getId());
        if (!originalOptional.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        if (!originalOptional.get().getSender().getId().equals(feed.getId()) && !securitySupportService.isAdmin()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Feed original = originalOptional.get();
        original.setContent(feed.getContent());
        // todo edit tags
        if (securitySupportService.isAdmin()) {
            original.setActive(feed.isActive());
        }

        Feed result = feedCrudManagerService.save(original);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, feedDTOTransfer.modelToDTO(result));
    }

    @Override
    public BusinessServiceExecuteResult<FeedDTO> create(FeedForSaveDTO feed) {
        if (!securitySupportService.isAllowed(feed.getSenderId())) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Feed f = new Feed();
        f.setContent(feed.getContent());

        Optional<User> optionalSender = userCrudManagerService.findOne(feed.getSenderId());
        if (!optionalSender.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (feed.getToUserId() != null) {
            Optional<User> optionalToUser = userCrudManagerService.findOne(feed.getToUserId());
            if (!optionalToUser.isPresent()) {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
            }
            f.setToUser(optionalToUser.get());
        }

        f.setSender(optionalSender.get());
        f.setActive(true);
        f.setDate(dateService.getCurrentDateInUTC());

        f = feedCrudManagerService.save(f);

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, feedDTOTransfer.modelToDTO(f));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<FeedDTO> findAll() {
        return feedCrudManagerService
                .findAll()
                .stream()
                .map(o -> feedDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FeedDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return feedCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FeedDTO> findMyFriendsFeed(Long iam) {
        return feedCrudManagerService
                .findMyFriendsFeed(iam)
                .stream()
                .map(o -> feedDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());

    }

    @Override
    public Page<FeedDTO> findMyFriendsFeed(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return feedCrudManagerService
                .findMyFriendsFeed(iam, page, size, sortType, sortProperties)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FeedDTO> findMyFriendsFeed() {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        return feedCrudManagerService
                .findMyFriendsFeed(auth)
                .stream()
                .map(o -> feedDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FeedDTO> findMyFriendsFeed(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        return feedCrudManagerService
                .findMyFriendsFeed(auth, page, size, sortType, sortProperties)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FeedDTO> findFeedsForMe(Long iam) {
        return feedCrudManagerService
                .findFeedsForMe(iam)
                .stream()
                .map(o -> feedDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FeedDTO> findFeedsForMe(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return feedCrudManagerService
                .findFeedsForMe(iam, page, size, sortType, sortProperties)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FeedDTO> findFeedsForMe() {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        return feedCrudManagerService
                .findFeedsForMe(auth)
                .stream()
                .map(o -> feedDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FeedDTO> findFeedsForMe(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        return feedCrudManagerService
                .findFeedsForMe(auth, page, size, sortType, sortProperties)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public Optional<FeedDTO> findFeedByIdAndToUserId(Long feedId, Long toUserId) {
        return feedCrudManagerService
                .findFeedByIdAndToUserId(feedId, toUserId)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public Optional<FeedDTO> findFeedByIdAndSenderId(Long feedId, Long senderId) {
        return feedCrudManagerService
                .findFeedByIdAndSenderId(feedId, senderId)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public Optional<FeedDTO> findFeedByIdAndSenderId(Long feedId) {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        return feedCrudManagerService
                .findFeedByIdAndSenderId(feedId, auth)
                .map(o -> feedDTOTransfer.modelToDTO(o));
    }

    @Override
    public Optional<FeedDTO> findOne(Long id) {
        if (securitySupportService.isAdmin()) {
            return feedCrudManagerService
                    .findOne(id)
                    .map(o -> feedDTOTransfer.modelToDTO(o));
        } else {
            Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
            return feedCrudManagerService
                    .findFeedByIdAndSenderId(id, auth)
                    .map(o -> feedDTOTransfer.modelToDTO(o));
        }
    }

    @Override
    public BusinessServiceExecuteResult<List<FeedDTO>> findFeedBySender(Long sender) {
        if (securitySupportService.isAllowedFull(sender)) {
            List<FeedDTO> list = feedCrudManagerService
                    .findFeedBySender(sender)
                    .stream()
                    .map(o -> feedDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, list);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
    }

    @Override
    public BusinessServiceExecuteResult<Page<FeedDTO>> findFeedBySender(Long sender, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (securitySupportService.isAllowedFull(sender)) {
            Page<FeedDTO> p = feedCrudManagerService
                    .findFeedBySender(sender, page, size, sortType, sortProperties)
                    .map(o -> feedDTOTransfer.modelToDTO(o));
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, p);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
    }
}
