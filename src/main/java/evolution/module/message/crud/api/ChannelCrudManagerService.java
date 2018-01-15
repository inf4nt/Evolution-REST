package evolution.module.message.crud.api;

import evolution.crud.AbstractCrudManagerService;
import evolution.crud.InitializeLazyCrudManagerService;
import evolution.crud.PageableManager;
import evolution.module.message.dto.ChannelSaveDTO;
import evolution.module.message.model.Channel;
import evolution.module.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ChannelCrudManagerService extends AbstractCrudManagerService<Channel, Long>, PageableManager, InitializeLazyCrudManagerService<Channel> {

    Optional<Channel> findOneLazy(Long id);

    List<Channel> findByName(String name);

    List<Channel> findByName(String name, String sortType, List<String> sortProperties);

    Page<Channel> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<Channel> findAllLazy();

    List<Channel> findAllLazy(String sortType, List<String> sortProperties);

    Page<Channel> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties);

    List<Channel> findChannelForChannelUser(Long userId);

    List<Channel> findChannelForChannelUser(Long userId, String sortType, List<String> sortProperties);

    Page<Channel> findChannelForChannelUser(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<User> findUserByChannelId(Long id);

    Optional<Channel> findChannelByIdLazyChannelUser(Long id);

    void clearRowByUserForeignKey(Long userId);

    List<Channel> findChannelForWhoCreateChannelUser(Long userId);

    List<Channel> findChannelForWhoCreateChannelUser(Long userId, String sortType, List<String> sortProperties);

    Page<Channel> findChannelForWhoCreateChannelUser(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties);

    Long countUserByChannel(Long id);

    List<User> findUserByChannel(Long channelId);

    List<User> findUserByChannel(Long channelId, String sortType, List<String> sortProperties);

    Page<User> findUserByChannel(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);

    Optional<Channel> createNewChannel(String channelName, Long whoCreateChannelId, boolean isPrivate);

    Optional<Channel> createNewChannel(ChannelSaveDTO channelSaveDTO);

    Optional<Channel> joinChannel(Long channelId, Long userId);

    Optional<Channel> outFromChannel(Long channelId, Long userId);

    void detach(Channel channel);
}
