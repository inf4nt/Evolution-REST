package evolution.business;

import evolution.business.api.MessageBusinessService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.dto.model.MessageDTO;
import evolution.dto.model.MessageSaveDTO;
import evolution.dto.model.MessageUpdateDTO;
import evolution.dto.transfer.MessageDTOTransfer;
import evolution.model.Dialog;
import evolution.model.Message;
import evolution.model.User;
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

    private final DateService dateService;

    @Autowired
    public MessageBusinessServiceImpl(MessageCrudManagerService messageCrudManagerService,
                                      SecuritySupportService securitySupportService,
                                      MessageDTOTransfer messageDTOTransfer,
                                      DateService dateService) {
        this.messageCrudManagerService = messageCrudManagerService;
        this.securitySupportService = securitySupportService;
        this.messageDTOTransfer = messageDTOTransfer;
        this.dateService = dateService;
    }

    @Override
    public Optional<MessageDTO> findOne(Long id) {
        if (securitySupportService.isAdmin()) {
            return messageDTOTransfer.modelToDTO(messageCrudManagerService.findOne(id));
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            return messageDTOTransfer.modelToDTO(messageCrudManagerService.findOne(id, auth.getId()));
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll() {
        return messageDTOTransfer
                .modelToDTO(messageCrudManagerService.findAll());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<MessageDTO> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageDTOTransfer
                .modelToDTO(messageCrudManagerService.findAll(page, size, sortType, sortProperties));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageDTO> findAll(String sortType, List<String> sortProperties) {
        return messageDTOTransfer
                .modelToDTO(messageCrudManagerService.findAll(sortType, sortProperties));
    }

    @Override
    public List<MessageDTO> findMessageByDialogId(Long dialogId) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        List<Message> list;

        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findMessageByDialogId(dialogId);
            if (!list.isEmpty()) {
                Dialog dialog = list.get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(list, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(list);
                }
            }
            return new ArrayList<>();
        }
        list = messageCrudManagerService.findMessageByDialogId(dialogId, auth.getId());
        return messageDTOTransfer.modelToDTO(list, auth.getId());
    }

    @Override
    public Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        Page<Message> p;

        if (securitySupportService.isAdmin()) {
            p = messageCrudManagerService
                    .findMessageByDialogId(dialogId, page, size, sortType, sortProperties);
            if (!p.getContent().isEmpty()) {
                Dialog dialog = p.getContent().get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(p, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(p);
                }
            }
            return new PageImpl<>(new ArrayList<>());
        }
        p = messageCrudManagerService.findMessageByDialogId(dialogId, auth.getId(), page, size, sortType, sortProperties);
        return messageDTOTransfer.modelToDTO(p, auth.getId());
    }

    @Override
    public List<MessageDTO> findMessageByDialogId(Long dialogId, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        List<Message> list;

        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findMessageByDialogId(dialogId, sortType, sortProperties);
            if (!list.isEmpty()) {
                Dialog dialog = list.get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(list, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(list);
                }
            }
            return new ArrayList<>();
        }
        list = messageCrudManagerService.findMessageByDialogId(dialogId, auth.getId(), sortType, sortProperties);
        return messageDTOTransfer.modelToDTO(list, auth.getId());
    }


    @Override
    public BusinessServiceExecuteResult<MessageDTO> createNewMessage(MessageSaveDTO messageSaveDTO) {
        Message message = messageCrudManagerService.saveMessageAndMaybeCreateNewDialog(messageSaveDTO, dateService.getCurrentDateInUTC());
        return BusinessServiceExecuteResult.build(OK, messageDTOTransfer.modelToDTO(message, securitySupportService.getAuthenticationPrincipal().getUser()));
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> createMessage(Long senderId, Long recipientId, String text) {
        if (!securitySupportService.isAllowedFull(senderId)) {
            logger.info("FORBIDDEN");
            return BusinessServiceExecuteResult.build(FORBIDDEN);
        }

        Message message = messageCrudManagerService.saveMessageAndMaybeCreateNewDialog(text, senderId, recipientId, dateService.getCurrentDateInUTC());
        return BusinessServiceExecuteResult.build(OK, messageDTOTransfer.modelToDTO(message, securitySupportService.getAuthenticationPrincipal().getUser()));
    }

    @Override
    public BusinessServiceExecuteResult delete(Long id) {
        if (securitySupportService.isAdmin()) {
            messageCrudManagerService.deleteMessageAndMaybeDialog(id);
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            messageCrudManagerService.deleteMessageAndMaybeDialog(id, auth.getId());
        }
        return BusinessServiceExecuteResult.build(OK);
    }

    @Override
    public BusinessServiceExecuteResult delete(List<Long> ids) {
        if (securitySupportService.isAdmin()) {
            messageCrudManagerService.deleteMessageAndMaybeDialog(ids);
        } else {
            User auth = securitySupportService.getAuthenticationPrincipal().getUser();
            messageCrudManagerService.deleteMessageAndMaybeDialog(ids, auth.getId());
        }
        return BusinessServiceExecuteResult.build(OK);
    }

    @Override
    public BusinessServiceExecuteResult<MessageDTO> update(MessageUpdateDTO messageUpdateDTO) {
        Optional<Message> original = messageCrudManagerService.findOne(messageUpdateDTO.getId());
        if (original.isPresent()) {
            if (securitySupportService.isAllowedFull(original.get().getSender().getId())) {
                Message m = original.get();
                m.setMessage(messageUpdateDTO.getMessage());

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
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        List<Message> list;

        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findLastMessageInMyDialog(iam);
            if (!list.isEmpty()) {
                Dialog dialog = list.get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(list, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(list);
                }
            }
            return new ArrayList<>();
        } else if (securitySupportService.isAllowed(iam)) {
            list = messageCrudManagerService.findLastMessageInMyDialog(iam);
            return messageDTOTransfer.modelToDTO(list, auth.getId());
        }
        return new ArrayList<>();
    }

    @Override
    public List<MessageDTO> findLastMessageInMyDialog(Long iam, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        List<Message> list;

        if (securitySupportService.isAdmin()) {
            list = messageCrudManagerService
                    .findLastMessageInMyDialog(iam, sortType, sortProperties);
            if (!list.isEmpty()) {
                Dialog dialog = list.get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(list, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(list);
                }
            }
            return new ArrayList<>();
        } else if (securitySupportService.isAllowed(iam)) {
            list = messageCrudManagerService.findLastMessageInMyDialog(auth.getId(), sortType, sortProperties);
            return messageDTOTransfer.modelToDTO(list, auth);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<MessageDTO> findLastMessageInMyDialog(Long iam, Integer page, Integer size, String sortType, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        Page<Message> p;

        if (securitySupportService.isAdmin()) {
            p = messageCrudManagerService
                    .findLastMessageInMyDialog(iam, page, size, sortType, sortProperties);
            if (!p.getContent().isEmpty()) {
                Dialog dialog = p.getContent().get(0).getDialog();
                if (dialog.getFirst().getId().equals(auth.getId()) || dialog.getSecond().getId().equals(auth.getId())) {
                    return messageDTOTransfer.modelToDTO(p, auth);
                } else {
                    return messageDTOTransfer.modelToDTO(p);
                }
            }
            return new PageImpl<>(new ArrayList<>());
        } else if (securitySupportService.isAllowed(iam)) {
            p = messageCrudManagerService.findLastMessageInMyDialog(auth.getId(), page, size, sortType, sortProperties);
            return messageDTOTransfer.modelToDTO(p, auth);
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
    public List<MessageDTO> findMessageBySenderId(Long iam, String sortType, List<String> sortProperties) {
        return null;
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
    public List<MessageDTO> findMessageByRecipientId(Long iam, String sortType, List<String> sortProperties) {
        return null;
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
    public List<MessageDTO> findMessageByInterlocutor(Long interlocutor, String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Page<MessageDTO> findMessageByInterlocutor(Long interlocutor, Integer page, Integer size, String sort, List<String> sortProperties) {
        User auth = securitySupportService.getAuthenticationPrincipal().getUser();
        return messageCrudManagerService
                .findMessageByInterlocutor(interlocutor, auth.getId(), page, size, sort, sortProperties)
                .map(o -> messageDTOTransfer.modelToDTO(o));
    }
}
