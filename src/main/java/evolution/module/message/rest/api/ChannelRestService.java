package evolution.message.rest.api;

import evolution.message.dto.*;
import evolution.rest.api.AbstractRestService;
import evolution.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChannelRestService extends AbstractRestService {

    ResponseEntity<ChannelDTO> findOneChannel(Long id);

    ResponseEntity<ChannelDTOLazy> findOneChannelLazy(Long id);

    ResponseEntity<MessageChannelDTO> findOneMessage(Long id);

    ResponseEntity<List<ChannelDTO>> findAllChannel();

    ResponseEntity<List<ChannelDTO>> findAllChannel(String sortType, List<String> sortProperties);

    ResponseEntity<Page<ChannelDTO>> findAllChannel(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<ChannelDTOLazy>> findAllChannelLazy();

    ResponseEntity<List<ChannelDTOLazy>> findAllChannelLazy(String sortType, List<String> sortProperties);

    ResponseEntity<Page<ChannelDTOLazy>> findAllChannelLazy(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<MessageChannelDTO>> findAllMessage();

    ResponseEntity<List<MessageChannelDTO>>  findAllMessage(String sortType, List<String> sortProperties);

    ResponseEntity<Page<MessageChannelDTO>>  findAllMessage(Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<MessageChannelDTO>> findMessageByChannelId(Long channelId);

    ResponseEntity<List<MessageChannelDTO>> findMessageByChannelId(Long channelId, String sortType, List<String> sortProperties);

    ResponseEntity<Page<MessageChannelDTO>> findMessageByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(Long channelUserId);

    ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(Long channelUserId, String sortType, List<String> sortProperties);

    ResponseEntity<Page<ChannelDTO>> findChannelForChannelUser(Long channelUserId, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<ChannelDTO>> findByName(String name);

    ResponseEntity<List<ChannelDTO>> findByName(String name, String sortType, List<String> sortProperties);

    ResponseEntity<Page<ChannelDTO>> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<List<UserDTO>> findUserByChannelId(Long id);

    ResponseEntity<List<UserDTO>> findUserByChannelId(Long id, String sortType, List<String> sortProperties);

    ResponseEntity<Page<UserDTO>> findUserByChannelId(Long id, Integer page, Integer size, String sortType, List<String> sortProperties);

    ResponseEntity<Long> findCountMessageByChannelId(Long id);

    ResponseEntity<Long> countUserByChannel(Long id);

    ResponseEntity<ChannelDTO> createNewChannel(ChannelSaveDTO channelSaveDTO);

    ResponseEntity<ChannelDTOLazy> createNewChannel2(ChannelSaveDTO channelSaveDTO);

    ResponseEntity<HttpStatus> deleteChannel(Long id);

    ResponseEntity<ChannelDTO> updateChannel(ChannelUpdateDTO channelUpdateDTO);

    ResponseEntity<ChannelDTOLazy> updateChannel2(ChannelUpdateDTO channelUpdateDTO);

    ResponseEntity<MessageChannelDTO> createNewMessageChannel(MessageChannelSaveDTO messageChannelSaveDTO);

    ResponseEntity<HttpStatus> deleteMessageChannel(Long id);

    ResponseEntity<MessageChannelDTO> updateMessageChannel(MessageChannelUpdateDTO messageChannelUpdateDTO);

    ResponseEntity<ChannelDTO> joinToChannel(Long id);

    ResponseEntity<ChannelDTO> outFromChannel(Long id);
}
