package evolution.business;

import evolution.business.api.ChannelBusinessService;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.crud.api.ChannelCrudManagerService;
import evolution.crud.api.MessageChannelCrudManagerService;
import evolution.dto.model.*;
import evolution.dto.transfer.ChannelDTOTransfer;
import evolution.dto.transfer.MessageChannelDTOTransfer;
import evolution.dto.transfer.UserDTOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelBusinessServiceImpl implements ChannelBusinessService {

    private final ChannelCrudManagerService channelCrudManagerService;

    private final MessageChannelCrudManagerService messageChannelCrudManagerService;

    private final MessageChannelDTOTransfer messageChannelDTOTransfer;

    private final ChannelDTOTransfer channelDTOTransfer;

    private final UserDTOTransfer userDTOTransfer;

    @Autowired
    public ChannelBusinessServiceImpl(ChannelCrudManagerService channelCrudManagerService,
                                      MessageChannelCrudManagerService messageChannelCrudManagerService,
                                      MessageChannelDTOTransfer messageChannelDTOTransfer,
                                      ChannelDTOTransfer channelDTOTransfer,
                                      UserDTOTransfer userDTOTransfer) {
        this.channelCrudManagerService = channelCrudManagerService;
        this.messageChannelCrudManagerService = messageChannelCrudManagerService;
        this.messageChannelDTOTransfer = messageChannelDTOTransfer;
        this.channelDTOTransfer = channelDTOTransfer;
        this.userDTOTransfer = userDTOTransfer;
    }

    @Override
    public Optional<ChannelDTO> findOneChannel(Long id) {
        return channelDTOTransfer.modelToDTO(channelCrudManagerService.findOne(id));
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
    public Long findCountChannelUserByChannelId(Long id) {
        return channelCrudManagerService.findCountUserByChannelId(id);
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTO> createNewChannel(ChannelSaveDTO channelSaveDTO) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<ChannelDTOLazy> createNewChannel2(ChannelSaveDTO channelSaveDTO) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteChannel(Long id) {
        return null;
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
    public BusinessServiceExecuteResult<MessageChannelDTO> createNewMessageChannel(MessageChannelSaveDTO messageChannelSaveDTO) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteMessageChannel(Long id) {
        return null;
    }

    @Override
    public BusinessServiceExecuteResult<MessageChannelDTO> updateMessageChannel(MessageChannelUpdateDTO messageChannelUpdateDTO) {
        return null;
    }
}
