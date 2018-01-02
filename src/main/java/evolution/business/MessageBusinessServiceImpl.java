package evolution.business;

import evolution.business.api.MessageBusinessService;
import evolution.crud.api.DialogCrudManagerService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.modelOld.MessageDTO;
import evolution.dto.modelOld.MessageDTOForSave;
import evolution.dto.modelOld.MessageDTOFull;
import evolution.dto.modelOld.MessageForUpdateDTO;
import evolution.model.Message;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.DateService;
import evolution.service.SecuritySupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static evolution.common.BusinessServiceExecuteStatus.*;

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
    public Optional<MessageDTO> findOne(Long id) {
        if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findOne(id)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            return messageCrudManagerService
                    .findOneByMessageIdAndSenderId(id, auth.getId())
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll() {
        return messageCrudManagerService
                .findAll().stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<MessageDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findAll(page, size, sortType, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll(String sortType, List<String> sortProperties) {
        return messageCrudManagerService
                .findAll(sortType, sortProperties)
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> findMessageByDialogId(Long dialogId) {
        if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageByDialogId(dialogId)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            return messageCrudManagerService
                    .findMessageByDialogId(dialogId, auth.getId())
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageByDialogId(dialogId, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            return messageCrudManagerService
                    .findMessageByDialogId(dialogId, auth.getId(), page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        }
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
    public BusinessServiceExecuteResult<MessageDTO> createNewMessage(MessageDTOForSave messageDTOForSave) {
        return createMessage(messageDTOForSave.getSenderId(), messageDTOForSave.getRecipientId(), messageDTOForSave.getText());
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> createMessage(Long senderId, Long recipientId, String text) {
        if (!securitySupportService.isAllowedFull(senderId)) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(FORBIDDEN);
        }

        try {
            Message message = messageCrudManagerService.saveMessageAndMaybeCreateNewDialog(text, senderId, recipientId, dateService.getCurrentDateInUTC());
            return BusinessServiceExecuteResult.build(OK, messageDTOTransfer.modelToDTO(message, securitySupportService.getPrincipal()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getLocalizedMessage());
            return BusinessServiceExecuteResult.build(EXPECTATION_FAILED);
        }
    }

    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        try {
            boolean res;

            if (securitySupportService.isAdmin()) {
                res = messageCrudManagerService.deleteMessageAndMaybeDialog(id);
            } else {
                User auth = securitySupportService.getAuthenticationPrincipal().getUser();
                res = messageCrudManagerService.deleteMessageAndMaybeDialog(id, auth.getId());
            }

            if (res) {
                return BusinessServiceExecuteResult.build(OK);
            } else {
                return BusinessServiceExecuteResult.build(NOT_FOUNT_OBJECT_FOR_EXECUTE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            return BusinessServiceExecuteResult.build(CATCH_EXCPETION);
        }
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> update(MessageForUpdateDTO messageForUpdateDTO) {
        Optional<Message> original = messageCrudManagerService.findOne(messageForUpdateDTO.getId());
        if (original.isPresent()) {
            if (securitySupportService.isAllowedFull(original.get().getSender().getId())) {
                Message m = original.get();
                m.setMessage(messageForUpdateDTO.getContent());

                Optional<Message> res = messageCrudManagerService.update(m);
                return res
                        .map(message -> BusinessServiceExecuteResult.build(OK, messageDTOTransfer.modelToDTO(message)))
                        .orElseGet(() -> BusinessServiceExecuteResult.build(NOT_FOUNT_OBJECT_FOR_EXECUTE));

            } else {
                return BusinessServiceExecuteResult.build(FORBIDDEN);
            }
        } else {
            return BusinessServiceExecuteResult.build(NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
    }

    @Override
    public List<MessageDTO> findLastMessageInMyDialog(Long iam) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findLastMessageInMyDialog(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findLastMessageInMyDialog(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<MessageDTO> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findLastMessageInMyDialog(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findLastMessageInMyDialog(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public List<MessageDTO> findMessageBySenderId(Long iam) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findMessageBySenderId(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageBySenderId(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<MessageDTO> findMessageBySenderId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findMessageBySenderId(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageBySenderId(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public List<MessageDTO> findMessageByRecipientId(Long iam) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findMessageByRecipientId(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam))
                    .collect(Collectors.toList());
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageByRecipientId(iam)
                    .stream()
                    .map(o -> messageDTOTransfer.modelToDTO(o))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Page<MessageDTO> findMessageByRecipientId(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        if (securitySupportService.isAllowed(iam)) {
            return messageCrudManagerService
                    .findMessageByRecipientId(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o, iam));
        } else if (securitySupportService.isAdmin()) {
            return messageCrudManagerService
                    .findMessageByRecipientId(iam, page, size, sortType, sortProperties)
                    .map(o -> messageDTOTransfer.modelToDTO(o));
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public List<MessageDTO> findMessageByInterlocutor(Long interlocutor) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByInterlocutor(interlocutor, auth.getId())
                .stream()
                .map(o -> messageDTOTransfer.modelToDTO(o))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MessageDTO> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByInterlocutor(interlocutor, auth.getId(), page, size, sort, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o));
    }
}
