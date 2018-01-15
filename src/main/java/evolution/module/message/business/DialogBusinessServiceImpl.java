package evolution.module.message.business;

import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.module.message.business.api.DialogBusinessService;
import evolution.module.message.crud.api.DialogCrudManagerService;
import evolution.module.message.crud.api.MessageCrudManagerService;
import evolution.module.message.dto.DialogDTO;
import evolution.module.message.dto.DialogDTOLazy;
import evolution.module.message.dto.MessageDTO;
import evolution.module.message.dto.transfer.DialogDTOTransfer;
import evolution.module.message.dto.transfer.MessageDTOTransfer;
import evolution.module.message.model.Dialog;
import evolution.module.message.model.Message;
import evolution.module.security.service.SecuritySupportService;
import evolution.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DialogBusinessServiceImpl implements DialogBusinessService {

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
    public BusinessServiceExecuteResult<List<DialogDTO>> findDialogsByUserId(Long userId) {
        if (!securitySupportService.isAllowedFull(userId)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        List<Dialog> list = dialogCrudManagerService.findDialogsByUserId(userId);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, repairDialogDTO(list, userId));
    }

    @Override
    public BusinessServiceExecuteResult<List<DialogDTO>> findDialogsByUserId(Long userId, String sortType, List<String> sortProperties) {
        if (!securitySupportService.isAllowedFull(userId)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        List<Dialog> list = dialogCrudManagerService.findDialogsByUserId(userId, sortType, sortProperties);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, repairDialogDTO(list, userId));
    }

    @Override
    public BusinessServiceExecuteResult<Page<DialogDTO>> findDialogsByUserId(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (!securitySupportService.isAllowedFull(userId)) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
        Page<Dialog> p = dialogCrudManagerService.findDialogsByUserId(userId, page, size, sortType, sortProperties);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, repairDialogDTO(p, userId));
    }

    @Override
    public Optional<DialogDTO> findOne(Long id) {
        return dialogDTOTransfer
                .modelToDTO(dialogCrudManagerService.findOne(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<DialogDTOLazy> findOneLazy(Long id) {
        return dialogDTOTransfer
                .modelToDTOLazy(dialogCrudManagerService.findOneLazy(id));
    }

    @Override
    public List<DialogDTO> findAll() {
        return dialogDTOTransfer
                .modelToDTO(dialogCrudManagerService.findAll());
    }

    @Override
    public List<DialogDTO> findAll(String sortType, List<String> sortProperties) {
        return dialogDTOTransfer
                .modelToDTO(dialogCrudManagerService.findAll(sortType, sortProperties));
    }

    @Override
    public Page<DialogDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return dialogDTOTransfer
                .modelToDTO(dialogCrudManagerService.findAll(page, size, sortType, sortProperties));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTOLazy> findAllLazy() {
        return dialogDTOTransfer
                .modelToDTOLazy(dialogCrudManagerService.findAllLazy());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTOLazy> findAllLazy(String sortType, List<String> sortProperties) {
        return dialogDTOTransfer
                .modelToDTOLazy(dialogCrudManagerService.findAllLazy(sortType, sortProperties));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<DialogDTOLazy> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return dialogDTOTransfer
                .modelToDTOLazy(dialogCrudManagerService.findAllLazy(page, size, sortType, sortProperties));
    }

    @Override
    public BusinessServiceExecuteResult<HttpStatus> delete(Long id) {
        dialogCrudManagerService.delete(id);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public List<MessageDTO> findMessageByDialog(Long dialogId) {
        List<Message> list;
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findMessageByDialogId(dialogId);
        } else {
            list = messageCrudManagerService
                    .findMessageByDialogIdAndParticipant(dialogId, auth.getId());
        }

        return repairMessageDTO(list, auth.getId());
    }

    @Override
    public List<MessageDTO> findMessageByDialog(Long dialogId, String sortType, List<String> sortProperties) {
        List<Message> list;
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findMessageByDialogId(dialogId, sortType, sortProperties);
        } else {
            list = messageCrudManagerService
                    .findMessageByDialogIdAndParticipant(dialogId, auth.getId(), sortType, sortProperties);
        }
        return repairMessageDTO(list, auth.getId());
    }

    @Override
    public Page<MessageDTO> findMessageByDialog(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Page<Message> p;
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        if (securitySupportService.isAdmin()) {
            p = messageCrudManagerService
                    .findMessageByDialogId(dialogId, page, size, sortType, sortProperties);
        } else {
            p = messageCrudManagerService
                    .findMessageByDialogIdAndParticipant(dialogId, auth.getId(), page, size, sortType, sortProperties);
        }
        return repairMessageDTO(p, auth.getId());
    }

    private List<DialogDTO> repairDialogDTO(List<Dialog> list, Long auth) {
        if (securitySupportService.isAllowed(auth)) {
            return dialogDTOTransfer.modelToDTO(list, auth);
        } else {
            return dialogDTOTransfer.modelToDTO(list);
        }
    }

    private Page<DialogDTO> repairDialogDTO(Page<Dialog> page, Long auth) {
        if (securitySupportService.isAllowed(auth)) {
            return dialogDTOTransfer.modelToDTO(page, auth);
        } else {
            return dialogDTOTransfer.modelToDTO(page);
        }
    }

    private List<MessageDTO> repairMessageDTO(List<Message> list, Long auth) {
        if (securitySupportService.isAllowed(auth)) {
            return messageDTOTransfer.modelToDTO(list, auth);
        } else {
            return messageDTOTransfer.modelToDTO(list);
        }
    }

    private Page<MessageDTO> repairMessageDTO(Page<Message> page, Long auth) {
        if (securitySupportService.isAllowed(auth)) {
            return messageDTOTransfer.modelToDTO(page, auth);
        } else {
            return messageDTOTransfer.modelToDTO(page);
        }
    }
}
