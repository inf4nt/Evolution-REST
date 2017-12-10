package evolution.business;

import evolution.business.api.FriendBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.FriendActionEnum;
import evolution.common.RelationshipStatus;
import evolution.crud.api.FriendCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.FriendDTOTransfer;
import evolution.dto.UserDTOTransfer;
import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
import evolution.dto.model.FriendResultActionDTO;
import evolution.model.Friend;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendBusinessServiceImpl implements FriendBusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FriendCrudManagerService friendCrudManagerService;

    private final SecuritySupportService securitySupportService;

    private final FriendDTOTransfer friendDTOTransfer;

    private final UserDTOTransfer userDTOTransfer;

    private final UserCrudManagerService userCrudManagerService;

    @Autowired
    public FriendBusinessServiceImpl(FriendCrudManagerService friendCrudManagerService,
                                     SecuritySupportService securitySupportService,
                                     FriendDTOTransfer friendDTOTransfer,
                                     UserDTOTransfer userDTOTransfer,
                                     UserCrudManagerService userCrudManagerService) {
        this.friendCrudManagerService = friendCrudManagerService;
        this.securitySupportService = securitySupportService;
        this.friendDTOTransfer = friendDTOTransfer;
        this.userDTOTransfer = userDTOTransfer;
        this.userCrudManagerService = userCrudManagerService;
    }


    @Override
    public List<FriendDTOFull> findAll2() {
        return friendCrudManagerService
                .findAll()
                .stream()
                .map(o -> friendDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FriendDTOFull> findAll2(Integer page, Integer size) {
        return friendCrudManagerService
                .findAll(page, size)
                .map(o -> friendDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public List<FriendDTOFull> findFriends2(Long iam) {
        return friendCrudManagerService
                .findProgressByUser(iam)
                .stream()
                .map(o -> friendDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FriendDTOFull> findFriends2(Long iam, Integer page, Integer size) {
        return friendCrudManagerService
                .findProgressByUser(iam, page, size)
                .map(o -> friendDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public List<FriendDTOFull> findFollowers2(Long iam) {
        return friendCrudManagerService
                .findFollowerByUser(iam)
                .stream()
                .map(o -> friendDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FriendDTOFull> findFollowers2(Long iam, Integer page, Integer size) {
        return friendCrudManagerService
                .findFollowerByUser(iam, page, size)
                .map(o -> friendDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public List<FriendDTOFull> findRequests2(Long iam) {
        return friendCrudManagerService
                .findFollowerByUser(iam)
                .stream()
                .map(o -> friendDTOTransfer.modelToDTOFull(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FriendDTOFull> findRequests2(Long iam, Integer page, Integer size) {
        return friendCrudManagerService
                .findFollowerByUser(iam, page, size)
                .map(o -> friendDTOTransfer.modelToDTOFull(o));
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> acceptRequest(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        FriendResultActionDTO dto = new FriendResultActionDTO();
        dto.setNextAction(FriendActionEnum.SEND_REQUEST_TO_FRIEND);
        Optional<Friend> request = friendCrudManagerService.acceptRequest(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (request.isPresent() && request.get().getVersion() == null) {
            dto.setNextAction(getNextAction(request.get()));

        } else {
            dto = friendDTOTransfer.modelToResultActionDTO(request.get());
            dto.setNextAction(getNextAction(request.get()));
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> deleteFriend(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        FriendResultActionDTO dto = new FriendResultActionDTO();
        dto.setNextAction(FriendActionEnum.SEND_REQUEST_TO_FRIEND);
        Optional<Friend> delete = friendCrudManagerService.removeFriend(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (delete.isPresent()) {
            dto = friendDTOTransfer.modelToResultActionDTO(delete.get());
            dto.setNextAction(getNextAction(delete.get()));
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> deleteRequest(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> deleteRequest = friendCrudManagerService.removeRequest(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        FriendResultActionDTO dto = new FriendResultActionDTO();
        dto.setNextAction(FriendActionEnum.SEND_REQUEST_TO_FRIEND);
        if (deleteRequest.isPresent()) {
            dto = new FriendResultActionDTO();
            dto.setNextAction(getNextAction(deleteRequest.get()));
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> sendRequestToFriend(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> friend = friendCrudManagerService.sendRequestToFriend(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        FriendResultActionDTO dto = new FriendResultActionDTO();
        dto.setNextAction(FriendActionEnum.SEND_REQUEST_TO_FRIEND);
        if (friend.isPresent()) {
            dto = new FriendResultActionDTO();
            dto.setNextAction(getNextAction(friend.get()));
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
    }

    @Override
    public Optional<FriendDTO> findOne(Long first, Long second) {
        return friendCrudManagerService
                .findOneFriend(first, second)
                .map(o -> friendDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FriendDTO> findAll() {
        return friendCrudManagerService
                .findAll()
                .stream()
                .map(o -> friendDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FriendDTO> findAll(Integer page, Integer size) {
        return friendCrudManagerService
                .findAll(page, size)
                .map(o -> friendDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<FriendDTO> findFriends(Long iam) {
        List<Friend> progress = friendCrudManagerService
                .findProgressByUser(iam);
        return result(progress, iam);
    }

    @Override
    public Page<FriendDTO> findFriends(Long iam, Integer page, Integer size) {
        Page<Friend> p = friendCrudManagerService
                .findProgressByUser(iam, page, size);
        return result(p, iam);
    }

    @Override
    public List<FriendDTO> findFollowers(Long iam) {
        List<Friend> followers = friendCrudManagerService
                .findFollowerByUser(iam);
       return result(followers, iam);
    }

    @Override
    public Page<FriendDTO> findFollowers(Long iam, Integer page, Integer size) {
        Page<Friend> p = friendCrudManagerService
                .findFollowerByUser(iam, page, size);
        return result(p, iam);
    }

    @Override
    public List<FriendDTO> findRequests(Long iam) {
        if (securitySupportService.isAllowed(iam)) {
            return friendCrudManagerService
                    .findRequestByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findRequestByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<FriendDTO> findRequests(Long iam, Integer page, Integer size) {
        if (securitySupportService.isAllowedFull(iam)) {
            return friendCrudManagerService
                    .findRequestByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findRequestByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> action(FriendActionDTO actionDTO) {

        if (actionDTO.getAction() == FriendActionEnum.ACCEPT_REQUEST) {
            return acceptRequest(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.DELETE_FRIEND) {
            return deleteFriend(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.DELETE_REQUEST) {
            return deleteRequest(actionDTO);
        } else if (actionDTO.getAction() == FriendActionEnum.SEND_REQUEST_TO_FRIEND) {
            return sendRequestToFriend(actionDTO);
        }

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
    }

    @Override
    public FriendActionEnum getNextAction(Friend friend) {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();

        if (friend.getStatus() == RelationshipStatus.NOT_FOUND) {
            return FriendActionEnum.SEND_REQUEST_TO_FRIEND;
        }

        if (friend.getStatus() == RelationshipStatus.ACCEPTED) {
            return FriendActionEnum.DELETE_FRIEND;
        }

        if (friend.getStatus() == RelationshipStatus.PENDING && friend.getActionUser().equalsById(auth)) {
            return FriendActionEnum.DELETE_REQUEST;
        }

        if (friend.getStatus() == RelationshipStatus.PENDING && !friend.getActionUser().equalsById(auth)) {
            return FriendActionEnum.ACCEPT_REQUEST;
        }

        return FriendActionEnum.SEND_REQUEST_TO_FRIEND;
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> findNextAction(Long first, Long second) {
        if (securitySupportService.isAllowedFull(first) || securitySupportService.isAllowedFull(second)) {
            Optional<Friend> next = friendCrudManagerService.findOneFriend(first, second);
            if (!next.isPresent()) {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
            }

            FriendResultActionDTO dto = friendDTOTransfer.modelToResultActionDTO(next.get());
            dto.setNextAction(getNextAction(next.get()));

            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
    }

    @Override
    public BusinessServiceExecuteResult<FriendResultActionDTO> findNextAction(Long second) {
        Long auth = securitySupportService.getAuthenticationPrincipal().getUser().getId();
        FriendResultActionDTO dto;

        Optional<Friend> next = friendCrudManagerService.findOneFriend(auth, second);
        if (!next.isPresent()) {
            dto = new FriendResultActionDTO();
            dto.setNextAction(FriendActionEnum.SEND_REQUEST_TO_FRIEND);
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);
        }

        dto = friendDTOTransfer.modelToResultActionDTO(next.get());
        dto.setNextAction(getNextAction(next.get()));

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, dto);

    }


    private List<FriendDTO> result(List<Friend> list, Long iam) {
        return list.stream()
                .map(o -> friendDTOTransfer.modelToDTO(o, iam))
                .collect(Collectors.toList());
    }

    private Page<FriendDTO> result(Page<Friend> page, Long iam) {
        return page.map(o -> friendDTOTransfer.modelToDTO(o, iam));
    }
}
