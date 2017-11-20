package evolution.business;

import evolution.business.api.DialogBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.dto.DialogDTOTransfer;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.model.DialogDTO;
import evolution.dto.model.MessageDTO;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DialogBusinessServiceImpl implements DialogBusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DialogCrudManagerService dialogCrudManagerService;

    private final DialogDTOTransfer dialogDTOTransfer;

    private final SecuritySupportService securitySupportService;

    private final MessageCrudManagerService messageCrudManagerService;

    private final MessageDTOTransfer messageDTOTransfer;

    @Autowired
    public DialogBusinessServiceImpl(DialogCrudManagerService dialogCrudManagerService,
                                     DialogDTOTransfer dialogDTOTransfer,
                                     SecuritySupportService securitySupportService,
                                     MessageCrudManagerService messageCrudManagerService,
                                     MessageDTOTransfer messageDTOTransfer) {
        this.dialogCrudManagerService = dialogCrudManagerService;
        this.dialogDTOTransfer = dialogDTOTransfer;
        this.securitySupportService = securitySupportService;
        this.messageCrudManagerService = messageCrudManagerService;
        this.messageDTOTransfer = messageDTOTransfer;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<DialogDTO> findOne(Long id) {
        Optional<CustomSecurityUser> auth = securitySupportService.getPrincipal();
        return dialogCrudManagerService
                .findOne(id)
                .map(o -> dialogDTOTransfer.modelToDTO(o, auth));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTO> findAll() {
        return dialogCrudManagerService
                .findAll()
                .stream()
                .map(o -> dialogDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTO> findAll(String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAll(sortType, sortProperties)
                .stream()
                .map(o -> dialogDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<DialogDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(o -> dialogDTOTransfer.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult delete(Long id) {
        try {
            boolean a = dialogCrudManagerService.deleteById(id);
            if (a) {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
            } else {
                return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.CATCH_EXCPETION);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findMessageByDialog(Long dialogId) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId)
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findMessageByDialog(Long dialogId, String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, sortType, sortProperties)
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<MessageDTO> findMessageByDialog(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o));
    }

    @Override
    public List<MessageDTO> findMessageByDialogAndUserId(Long dialogId) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId())
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> findMessageByDialogAndUserId(Long dialogId, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId(), sortType, sortProperties)
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o, auth))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MessageDTO> findMessageByDialogAndUserId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByDialogId(dialogId, auth.getId(), page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o, auth));
    }
}
