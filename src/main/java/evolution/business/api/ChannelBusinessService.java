package evolution.business.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.*;
import evolution.model.channel.Channel;
import evolution.model.channel.MessageChannel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ChannelBusinessService {

    Optional<ChannelDTO> findOneChannel(Long id);

    Optional<Channel> findOneChannelModel(Long id);

    Optional<ChannelDTOLazy> findOneChannelLazy(Long id);

    Optional<MessageChannelDTO> findOneMessage(Long id);

    List<ChannelDTO> findAllChannel();

    List<ChannelDTO> findAllChannel(String sortType, List<String> sortProperties);

    Page<ChannelDTO> findAllChannel(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<ChannelDTOLazy> findAllChannelLazy();

    List<ChannelDTOLazy> findAllChannelLazy(String sortType, List<String> sortProperties);

    Page<ChannelDTOLazy> findAllChannelLazy(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageChannelDTO> findAllMessage();

    List<MessageChannelDTO> findAllMessage(String sortType, List<String> sortProperties);

    Page<MessageChannelDTO> findAllMessage(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageChannelDTO> findMessageByChannelId(Long channelId);

    List<MessageChannelDTO> findMessageByChannelId(Long channelId, String sortType, List<String> sortProperties);

    Page<MessageChannelDTO> findMessageByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<ChannelDTO> findChannelForChannelUser(Long channelUserId);

    List<ChannelDTO> findChannelForChannelUser(Long channelUserId, String sortType, List<String> sortProperties);

    Page<ChannelDTO> findChannelForChannelUser(Long channelUserId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<ChannelDTO> findByName(String name);

    List<ChannelDTO> findByName(String name, String sortType, List<String> sortProperties);

    Page<ChannelDTO> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<UserDTO> findUserByChannelId(Long id);

    List<UserDTO> findUserByChannelId(Long id, String sortType, List<String> sortProperties);

    Page<UserDTO> findUserByChannelId(Long id, Integer page, Integer size, String sortType, List<String> sortProperties);

    Long findCountMessageByChannelId(Long id);

    Long findCountChannelUserByChannelId(Long id);

    BusinessServiceExecuteResult<ChannelDTO> createNewChannel3(ChannelSaveDTO channelSaveDTO);

    BusinessServiceExecuteResult<ChannelDTOLazy> createNewChannel2(ChannelSaveDTO channelSaveDTO);

    BusinessServiceExecuteResult<Channel> createNewChannel(ChannelSaveDTO channelSaveDTO);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteChannel(Long id);

    BusinessServiceExecuteResult<ChannelDTO> updateChannel(ChannelUpdateDTO channelUpdateDTO);

    BusinessServiceExecuteResult<ChannelDTOLazy> updateChannel2(ChannelUpdateDTO channelUpdateDTO);

    BusinessServiceExecuteResult<MessageChannelDTO> createNewMessageChannel(MessageChannelSaveDTO messageChannelSaveDTO);

    BusinessServiceExecuteResult<MessageChannel> createNewMessageChannel2(MessageChannelSaveDTO messageChannelSaveDTO);

    BusinessServiceExecuteResult<BusinessServiceExecuteStatus> deleteMessageChannel(Long id);

    BusinessServiceExecuteResult<MessageChannelDTO> updateMessageChannel(MessageChannelUpdateDTO messageChannelUpdateDTO);


}
