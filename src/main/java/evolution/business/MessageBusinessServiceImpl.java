package evolution.business;

import evolution.business.api.MessageBusinessService;
import evolution.crud.api.MessageCrudManagerService;
import evolution.dto.MessageDTOTransfer;
import evolution.dto.model.MessageDTO;
import evolution.model.User;
import evolution.security.model.CustomSecurityUser;
import evolution.service.SecuritySupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Infant on 13.11.2017.
 */
@Service
public class MessageBusinessServiceImpl implements MessageBusinessService {

    private final MessageCrudManagerService messageCrudManagerService;

    private final SecuritySupportService securitySupportService;

    private final MessageDTOTransfer messageDTOTransfer;

    @Autowired
    public MessageBusinessServiceImpl(MessageCrudManagerService messageCrudManagerService,
                                      SecuritySupportService securitySupportService,
                                      MessageDTOTransfer messageDTOTransfer) {
        this.messageCrudManagerService = messageCrudManagerService;
        this.securitySupportService = securitySupportService;
        this.messageDTOTransfer = messageDTOTransfer;
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
    public List<MessageDTO> findMessageByDialogId(Long dialogId) {
        return null;
    }

    @Override
    public Page<MessageDTO> findMessageByDialogId(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<MessageDTO> findMessageByDialogId(Long dialogId, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId) {
        return null;
    }

    @Override
    public Page<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return null;
    }

    @Override
    public List<MessageDTO> findMessageByDialogIdAndUserIam(Long dialogId, String sortType, List<String> sortProperties) {
        return null;
    }

}
