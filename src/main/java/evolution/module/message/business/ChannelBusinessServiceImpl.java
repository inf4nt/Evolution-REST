package evolution.module.message.business;

import evolution.business.BusinessServiceExecuteResult;
import evolution.module.message.business.api.ChannelBusinessService;
import evolution.module.message.crud.api.ChannelCrudManagerService;
import evolution.module.message.crud.api.MessageChannelCrudManagerService;
import evolution.module.message.dto.*;
import evolution.module.message.dto.transfer.ChannelDTOTransfer;
import evolution.module.message.dto.transfer.MessageChannelDTOTransfer;
import evolution.module.message.model.Channel;
import evolution.module.message.model.MessageChannel;
import evolution.module.security.service.SecuritySupportService;
import evolution.module.user.business.api.UserBusinessService;
import evolution.module.user.dto.UserDTO;
import evolution.module.user.dto.transfer.UserDTOTransfer;
import evolution.module.user.model.User;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelBusinessServiceImpl implements ChannelBusinessService {

    private final ChannelCrudManagerService channelCrudManagerService;

    private final MessageChannelCrudManagerService messageChannelCrudManagerService;

    private final MessageChannelDTOTransfer messageChannelDTOTransfer;

    private final ChannelDTOTransfer channelDTOTransfer;

    private final UserDTOTransfer userDTOTransfer;

    private final UserBusinessService userBusinessService;

    private final DateService dateService;

    private final SecuritySupportService securitySupportService;

    @Autowired
    public ChannelBusinessServiceImpl(ChannelCrudManagerService channelCrudManagerService,
                                      MessageChannelCrudManagerService messageChannelCrudManagerService,
                                      MessageChannelDTOTransfer messageChannelDTOTransfer,
                                      ChannelDTOTransfer channelDTOTransfer,
                                      UserDTOTransfer userDTOTransfer,
                                      UserBusinessService userBusinessService,
                                      DateService dateService,
                                      SecuritySupportService securitySupportService) {
        this.channelCrudManagerService = channelCrudManagerService;
        this.messageChannelCrudManagerService = messageChannelCrudManagerService;
        this.messageChannelDTOTransfer = messageChannelDTOTransfer;
        this.channelDTOTransfer = channelDTOTransfer;
        this.userDTOTransfer = userDTOTransfer;
        this.userBusinessService = userBusinessService;
        this.dateService = dateService;
        this.securitySupportService = securitySupportService;
    }

    @Override
    public Optional<ChannelDTO> findOneChannel(Long id) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findOne(id));
    }

    @Override
    public Optional<Channel> findOneChannelModel(Long id) {
        return channelCrudManagerService.findOne(id);
    }

    @Override
    public Optional<ChannelDTOLazy> findOneChannelLazy(Long id) {
        return channelDTOTransfer.modelToDTOLazy(channelCrudManagerService.findOneLazy(id));
    }

    @Override
    public Optional<MessageChannelDTO> findOneMessage(Long id) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findOne(id));
    }

    @Override
    public List<ChannelDTO> findAllChannel() {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findAll());
    }

    @Override
    public List<ChannelDTO> findAllChannel(String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findAll(sortType, sortProperties));
    }

    @Override
    public Page<ChannelDTO> findAllChannel(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findAll(page, size, sortType, sortProperties));
    }

    @Override
    public List<ChannelDTOLazy> findAllChannelLazy() {
        return channelDTOTransfer.modelToDTOLazy(channelCrudManagerService.findAllLazy());
    }

    @Override
    public List<ChannelDTOLazy> findAllChannelLazy(String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTOLazy(channelCrudManagerService.findAllLazy(sortType, sortProperties));
    }

    @Override
    public Page<ChannelDTOLazy> findAllChannelLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTOLazy(channelCrudManagerService.findAllLazy(page, size, sortType, sortProperties));
    }

    @Override
    public List<MessageChannelDTO> findAllMessage() {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findAll());
    }

    @Override
    public List<MessageChannelDTO> findAllMessage(String sortType, List<String> sortProperties) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findAll(sortType, sortProperties));
    }

    @Override
    public Page<MessageChannelDTO> findAllMessage(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findAll(page, size, sortType, sortProperties));
    }

    @Override
    public List<MessageChannelDTO> findMessageByChannelId(Long channelId) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findMessageChannelByChannelId(channelId));
    }

    @Override
    public List<MessageChannelDTO> findMessageByChannelId(Long channelId, String sortType, List<String> sortProperties) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findMessageChannelByChannelId(channelId, sortType, sortProperties));
    }

    @Override
    public Page<MessageChannelDTO> findMessageByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return messageChannelDTOTransfer.modelToDTO(messageChannelCrudManagerService.findMessageChannelByChannelId(channelId, page, size, sortType, sortProperties));
    }

    @Override
    public List<ChannelDTO> findChannelForChannelUser(Long channelUserId) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findChannelForChannelUser(channelUserId));
    }

    @Override
    public List<ChannelDTO> findChannelForChannelUser(Long channelUserId, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findChannelForChannelUser(channelUserId, sortType, sortProperties));
    }

    @Override
    public Page<ChannelDTO> findChannelForChannelUser(Long channelUserId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findChannelForChannelUser(channelUserId, page, size, sortType, sortProperties));
    }

    @Override
    public List<ChannelDTO> findByName(String name) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findByName(name));
    }

    @Override
    public List<ChannelDTO> findByName(String name, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findByName(name, sortType, sortProperties));
    }

    @Override
    public Page<ChannelDTO> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findByName(name, page, size, sortType, sortProperties));
    }

    @Override
    public List<UserDTO> findUserByChannelId(Long id) {
        return userDTOTransfer.modelToDTO(channelCrudManagerService.findUserByChannel(id));
    }

    @Override
    public List<UserDTO> findUserByChannelId(Long id, String sortType, List<String> sortProperties) {
        return userDTOTransfer.modelToDTO(channelCrudManagerService.findUserByChannel(id, sortType, sortProperties));
    }

    @Override
    public Page<UserDTO> findUserByChannelId(Long id, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return userDTOTransfer.modelToDTO(channelCrudManagerService.findUserByChannel(id, page, size, sortType, sortProperties));
    }

    @Override
    public Long findCountMessageByChannelId(Long id) {
        return messageChannelCrudManagerService.findCountMessageChannelByChannelId(id);
    }

    @Override
    public Long countUserByChannel(Long id) {
        return channelCrudManagerService.countUserByChannel(id);
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> createNewChannel3(ChannelSaveDTO channelSaveDTO) {
        BusinessServiceExecuteResult<Channel> b = createNewChannel(channelSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, channelDTOTransfer.modelToDTO(b.getResultObject()));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<Channel> createNewChannel(ChannelSaveDTO channelSaveDTO) {
        Optional<Channel> oc = channelCrudManagerService.createNewChannel(channelSaveDTO);
        if (oc.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, oc.get());
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTOLazy> createNewChannel2(ChannelSaveDTO channelSaveDTO) {
        BusinessServiceExecuteResult<Channel> b = createNewChannel(channelSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, channelDTOTransfer.modelToDTOLazy(b.getResultObject()));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteChannel(Long id) {
        channelCrudManagerService.delete(id);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> updateChannel(ChannelUpdateDTO channelUpdateDTO) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTOLazy> updateChannel2(ChannelUpdateDTO channelUpdateDTO) {
        return null;
    }

    @Override
    @Transactional
    public BusinessServiceExecuteResult<MessageChannelDTO> createNewMessageChannel(MessageChannelSaveDTO messageChannelSaveDTO) {
        BusinessServiceExecuteResult<MessageChannel> b = createNewMessageChannel2(messageChannelSaveDTO);
        if (b.getExecuteStatus() == BusinessServiceExecuteStatus.OK && b.getResultObjectOptional().isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, messageChannelDTOTransfer.modelToDTO(b.getResultObject()));
        }
        return BusinessServiceExecuteResult.build(b.getExecuteStatus());
    }

    @Override
    public BusinessServiceExecuteResult<MessageChannel> createNewMessageChannel2(MessageChannelSaveDTO messageChannelSaveDTO) {
        Optional<User> ou = userBusinessService.findOneModel(messageChannelSaveDTO.getSenderId());
        if (!ou.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }
        Optional<Channel> oc = findOneChannelModel(messageChannelSaveDTO.getChannelId());
        if (!oc.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.NOT_FOUNT_OBJECT_FOR_EXECUTE);
        }

        MessageChannel messageChannel = new MessageChannel();
        messageChannel.setActive(true);
        messageChannel.setText(messageChannelSaveDTO.getText());
        messageChannel.setSender(ou.get());
        messageChannel.setChannel(oc.get());
        messageChannel.setDatePost(dateService.getCurrentDateInUTC());

        MessageChannel res = messageChannelCrudManagerService.save(messageChannel);
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, res);
    }

    @Override
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteMessageChannel(Long id) {
        if (securitySupportService.isAdmin()) {
            messageChannelCrudManagerService.delete(id);
        } else {
            messageChannelCrudManagerService.deleteByIdAndSenderId(id, securitySupportService.getAuthenticationPrincipal().getUser().getId());
        }
        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK);
    }

    @Override
    public BusinessServiceExecuteResult<MessageChannelDTO> updateMessageChannel(MessageChannelUpdateDTO messageChannelUpdateDTO) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> joinToChannel(Long id) {
        User user = securitySupportService.getAuthenticationPrincipal().getUser();
        return joinToChannel(id, user.getId());
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> joinToChannel(Long channelId, Long userId) {
        Optional<Channel> oc = channelCrudManagerService.joinChannel(channelId, userId);
        if (oc.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, channelDTOTransfer.modelToDTO(oc.get()));
        }

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> outFromChannel(Long id) {
        User user = securitySupportService.getAuthenticationPrincipal().getUser();
        Optional<Channel> oc = channelCrudManagerService.outFromChannel(id, user.getId());
        if (oc.isPresent()) {
            return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.OK, channelDTOTransfer.modelToDTO(oc.get()));
        }

        return BusinessServiceExecuteResult.build(BusinessServiceExecuteStatus.EXPECTATION_FAILED);
    }
}
