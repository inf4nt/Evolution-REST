package evolution.rest;

import evolution.business.api.ChannelBusinessService;
import evolution.dto.model.*;
import evolution.rest.api.ChannelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelRestServiceImpl implements ChannelRestService {

    private final ChannelBusinessService channelBusinessService;

    @Autowired
    public ChannelRestServiceImpl(ChannelBusinessService channelBusinessService) {
        this.channelBusinessService = channelBusinessService;
    }

    @Override
    public ResponseEntity<ChannelDTO> findOneChannel(Long id) {
        return response(channelBusinessService.findOneChannel(id));
    }

    @Override
    public ResponseEntity<ChannelDTOLazy> findOneChannelLazy(Long id) {
        return response(channelBusinessService.findOneChannelLazy(id));
    }

    @Override
    public ResponseEntity<MessageChannelDTO> findOneMessage(Long id) {
        return response(channelBusinessService.findOneMessage(id));
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findAllChannel() {
        return response(channelBusinessService.findAllChannel());
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findAllChannel(String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllChannel(sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<ChannelDTO>> findAllChannel(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllChannel(page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<ChannelDTOLazy>> findAllChannelLazy() {
        return response(channelBusinessService.findAllChannelLazy());
    }

    @Override
    public ResponseEntity<List<ChannelDTOLazy>> findAllChannelLazy(String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllChannelLazy(sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<ChannelDTOLazy>> findAllChannelLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllChannelLazy(page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<MessageChannelDTO>> findAllMessage() {
        return response(channelBusinessService.findAllMessage());
    }

    @Override
    public ResponseEntity<List<MessageChannelDTO>> findAllMessage(String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllMessage(sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<MessageChannelDTO>> findAllMessage(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findAllMessage(page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<MessageChannelDTO>> findMessageByChannelId(Long channelId) {
        return response(channelBusinessService.findMessageByChannelId(channelId));
    }

    @Override
    public ResponseEntity<List<MessageChannelDTO>> findMessageByChannelId(Long channelId, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findMessageByChannelId(channelId, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<MessageChannelDTO>> findMessageByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findMessageByChannelId(channelId, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(Long channelUserId) {
        return response(channelBusinessService.findChannelForChannelUser(channelUserId));
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findChannelForChannelUser(Long channelUserId, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findChannelForChannelUser(channelUserId, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<ChannelDTO>> findChannelForChannelUser(Long channelUserId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findChannelForChannelUser(channelUserId, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findByName(String name) {
        return response(channelBusinessService.findByName(name));
    }

    @Override
    public ResponseEntity<List<ChannelDTO>> findByName(String name, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findByName(name, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<ChannelDTO>> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findByName(name, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findUserByChannelId(Long id) {
        return response(channelBusinessService.findUserByChannelId(id));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findUserByChannelId(Long id, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findUserByChannelId(id, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Page<UserDTO>> findUserByChannelId(Long id, Integer page, Integer size, String sortType, List<String> sortProperties) {
        return response(channelBusinessService.findUserByChannelId(id, page, size, sortType, sortProperties));
    }

    @Override
    public ResponseEntity<Long> findCountMessageByChannelId(Long id) {
        return ResponseEntity.ok(channelBusinessService.findCountMessageByChannelId(id));
    }

    @Override
    public ResponseEntity<Long> findCountChannelUserByChannelId(Long id) {
        return ResponseEntity.ok(channelBusinessService.findCountChannelUserByChannelId(id));
    }

    @Override
    public ResponseEntity<ChannelDTO> createNewChannel(ChannelSaveDTO channelSaveDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ChannelDTOLazy> createNewChannel2(ChannelSaveDTO channelSaveDTO) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteChannel(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ChannelDTO> updateChannel(ChannelUpdateDTO channelUpdateDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ChannelDTOLazy> updateChannel2(ChannelUpdateDTO channelUpdateDTO) {
        return null;
    }

    @Override
    public ResponseEntity<MessageChannelDTO> createNewMessageChannel(MessageChannelSaveDTO messageChannelSaveDTO) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMessageChannel(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<MessageChannelDTO> updateMessageChannel(MessageChannelUpdateDTO messageChannelUpdateDTO) {
        return null;
    }
}
