package evolution.business;

import evolution.business.api.MessageBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageDTOForSave;
import evolution.dto.model.MessageDTOFull;
import evolution.model.Message;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static evolution.common.BusinessServiceExecuteStatus.EXPECTATION_FAILED;
import static evolution.common.BusinessServiceExecuteStatus.FORBIDDEN;
import static evolution.common.BusinessServiceExecuteStatus.OK;

/**
 * Created by Infant on 13.11.2017.
 */
@Service
public class MessageBusinessServiceImpl implements MessageBusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageCrudManagerService messageCrudManagerService;

    private final SecuritySupportService securitySupportService;

    private final MessageDTOTransfer messageDTOTransfer;

    private DialogCrudManagerService dialogCrudManagerService;

    private final UserCrudManagerService userCrudManagerService;

    private final DateService dateService;

    @Autowired
    public MessageBusinessServiceImpl(MessageCrudManagerService messageCrudManagerService,
                                      SecuritySupportService securitySupportService,
                                      MessageDTOTransfer messageDTOTransfer, UserCrudManagerService userCrudManagerService, DateService dateService) {
        this.messageCrudManagerService = messageCrudManagerService;
        this.securitySupportService = securitySupportService;
        this.messageDTOTransfer = messageDTOTransfer;
        this.userCrudManagerService = userCrudManagerService;
        this.dateService = dateService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll() {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        return messageCrudManagerService
                .findAll().stream()
                .map(o -> messageDTOTransfer.modelToDTO(o, optional))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<MessageDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        return messageCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o, optional));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll(String sortType, List<String> sortProperties) {
        Optional<CustomSecurityUser> optional = securitySupportService.getPrincipal();
        return messageCrudManagerService
                .findAll(sortType, sortProperties)
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o, optional))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findMessageByDialogId(Long dialogId) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId)
                .stream().map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findMessageByDialogId(Long dialogId, String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, sortType, sortProperties).stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId())
                .stream().map(o -> messageDTOTransfer.modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId(), page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o, auth));
    }

    @Override
    public List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId(), sortType, sortProperties).stream()
                .map(o -> messageDTOTransfer.modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTOForSave> createNewMessage(MessageDTOForSave messageDTOForSave) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> createNewMessage2(MessageDTOForSave messageDTOForSave) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTOFull> createNewMessage3(MessageDTOForSave messageDTOForSave) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTOForSave> createMessage(Long senderId, Long recipientId, String text) {
        if (!securitySupportService.isAllowed(senderId)) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(FORBIDDEN);
        }

        try {
            Message message = messageCrudManagerService.saveMessageAndMaybeCreateNewDialog(text, senderId, recipientId, dateService.getCurrentDateInUTC());
            return BusinessServiceExecuteResult.build(OK, messageDTOTransfer.modelToDTOForSave(message, securitySupportService.getPrincipal()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getLocalizedMessage());
            return BusinessServiceExecuteResult.build(EXPECTATION_FAILED);
        }
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> createMessage2(Long senderId, Long recipientId, String text) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTOFull> createMessage3(Long senderId, Long recipientId, String text) {
        return null;
    }


}
