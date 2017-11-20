package evolution.business;

import evolution.business.api.FriendBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.common.FriendStatusEnum;
import evolution.crud.api.FriendCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.FriendDTOTransfer;
import evolution.dto.UserDTOTransfer;
import evolution.dto.model.FriendActionDTO;
import evolution.dto.model.FriendDTO;
import evolution.dto.model.FriendDTOFull;
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
    public BusinessServiceExecuteResult<FriendDTOFull> acceptRequest(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> request = friendCrudManagerService.acceptRequest(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (!request.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (request.get().getStatus() == FriendStatusEnum.PROGRESS) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, friendDTOTransfer.modelToDTOFull(request.get()));
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED, friendDTOTransfer.modelToDTOFull(request.get()));
        }
    }

    @Override
    public BusinessServiceExecuteResult<FriendDTOFull> deleteFriend(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> delete = friendCrudManagerService.removeFriend(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (!delete.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (delete.get().getStatus() == FriendStatusEnum.REQUEST) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, friendDTOTransfer.modelToDTOFull(delete.get()));
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED, friendDTOTransfer.modelToDTOFull(delete.get()));
        }
    }

    @Override
    public BusinessServiceExecuteResult<FriendDTOFull> deleteRequest(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> deleteRequest = friendCrudManagerService.removeRequest(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (!deleteRequest.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (deleteRequest.get().getVersion() == null) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED, friendDTOTransfer.modelToDTOFull(deleteRequest.get()));
        }
    }

    @Override
    public BusinessServiceExecuteResult<FriendDTOFull> sendRequestToFriend(FriendActionDTO friendActionDTO) {
        if (!securitySupportService.isAllowedFull(friendActionDTO.getActionUserId())) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }

        Optional<Friend> friend = friendCrudManagerService.sendRequestToFriend(friendActionDTO.getActionUserId(), friendActionDTO.getRecipientUserId());
        if (!friend.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        if (friend.get().getStatus() == FriendStatusEnum.REQUEST) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, friendDTOTransfer.modelToDTOFull(friend.get()));
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED, friendDTOTransfer.modelToDTOFull(friend.get()));
        }
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
        if (securitySupportService.isAllowedFull(iam)) {
            return friendCrudManagerService
                    .findProgressByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findProgressByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<FriendDTO> findFriends(Long iam, Integer page, Integer size) {
        if (securitySupportService.isAllowedFull(iam)) {
            return friendCrudManagerService
                    .findProgressByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findProgressByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public List<FriendDTO> findFollowers(Long iam) {
        if (securitySupportService.isAllowedFull(iam)) {
            return friendCrudManagerService
                    .findFollowerByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findFollowerByUser(iam)
                    .stream()
                    .map(o -> friendDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<FriendDTO> findFollowers(Long iam, Integer page, Integer size) {
        if (securitySupportService.isAllowedFull(iam)) {
            return friendCrudManagerService
                    .findFollowerByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return friendCrudManagerService
                    .findFollowerByUser(iam, page, size)
                    .map(o -> friendDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public List<FriendDTO> findRequests(Long iam) {
        if (securitySupportService.isAllowedFull(iam)) {
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

}
