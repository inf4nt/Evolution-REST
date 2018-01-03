package evolution.business;

import evolution.business.api.DialogBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.dto.model.DialogDTO;
import evolution.dto.model.DialogDTOLazy;
import evolution.dto.model.MessageDTO;
import evolution.dto.transfer.DialogDTOTransferNew;
import evolution.dto.transfer.MessageDTOTransferNew;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DialogBusinessServiceImpl implements DialogBusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DialogCrudManagerService dialogCrudManagerService;

    private final DialogDTOTransferNew dialogDTOTransferNew;

    private final SecuritySupportService securitySupportService;

    private final MessageCrudManagerService messageCrudManagerService;

    private final MessageDTOTransferNew messageDTOTransferNew;

    @Autowired
    public DialogBusinessServiceImpl(DialogCrudManagerService dialogCrudManagerService,
                                     DialogDTOTransferNew dialogDTOTransferNew,
                                     SecuritySupportService securitySupportService,
                                     MessageCrudManagerService messageCrudManagerService,
                                     MessageDTOTransferNew messageDTOTransferNew) {
        this.dialogCrudManagerService = dialogCrudManagerService;
        this.dialogDTOTransferNew = dialogDTOTransferNew;
        this.securitySupportService = securitySupportService;
        this.messageCrudManagerService = messageCrudManagerService;
        this.messageDTOTransferNew = messageDTOTransferNew;
    }

    @Override
    public BusinessServiceExecuteResult<List<DialogDTO>> findDialogsByUserId(Long userId) {
        if (securitySupportService.isAllowed(userId)) {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK,
                    dialogCrudManagerService
                            .findDialogsByUserId(userId)
                            .stream()
                            .map(o -> dialogDTOTransferNew.modelToDTO(o, auth))
                            .collect(Collectors.toList()));
        } else {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.FORBIDDEN);
        }
    }

    @Override
    public Optional<DialogDTO> findOne(Long id) {
        return dialogCrudManagerService
                .findOne(id)
                .map(o -> dialogDTOTransferNew.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<DialogDTOLazy> findOneLazy(Long id) {
        return dialogCrudManagerService
                .findOneLazy(id)
                .map(o -> dialogDTOTransferNew.modelToDTOLazy(o));
    }

    @Override
    public List<DialogDTO> findAll() {
        return dialogCrudManagerService
                .findAll()
                .stream()
                .map(o -> dialogDTOTransferNew.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public List<DialogDTO> findAll(String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAll(sortType, sortProperties)
                .stream()
                .map(o -> dialogDTOTransferNew.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DialogDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(o -> dialogDTOTransferNew.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTOLazy> findAllLazy() {
        return dialogCrudManagerService
                .findAllLazy()
                .stream()
                .map(o -> dialogDTOTransferNew.modelToDTOLazy(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<DialogDTOLazy> findAllLazy(String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAllLazy(sortType, sortProperties)
                .stream()
                .map(o -> dialogDTOTransferNew.modelToDTOLazy(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<DialogDTOLazy> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return dialogCrudManagerService
                .findAllLazy(page, size, sortType, sortProperties)
                .map(o -> dialogDTOTransferNew.modelToDTOLazy(o));
    }

    @Override
    public BusinessServiceExecuteResult<HttpStatus> delete(Long id) {
        dialogCrudManagerService.delete(id);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public List<MessageDTO> findMessageByDialog(Long dialogId) {
        List<MessageDTO> list = new ArrayList<>();
        Optional<Dialog> op;
        if (securitySupportService.isAdmin()) {
            op = dialogCrudManagerService.findOneLazy(dialogId);
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            op = dialogCrudManagerService
                    .findOneLazyAndParticipantId(dialogId, auth.getId());
        }
        op.ifPresent(dialog -> dialog.getMessageList().forEach(o -> {
            list.add(messageDTOTransferNew.modelToDTO(o));
        }));
        return list;
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

        return list.stream()
                .map(o -> messageDTOTransferNew.modelToDTO(o, auth))
                .collect(Collectors.toList());
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
        return messageDTOTransferNew.modelToDTO(p, auth);
    }
}
