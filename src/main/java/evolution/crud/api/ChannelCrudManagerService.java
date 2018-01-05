package evolution.crud.api;

import evolution.business.BusinessServiceExecuteResult;
import evolution.common.BusinessServiceExecuteStatus;
import evolution.dto.model.ChannelDTO;
import evolution.dto.model.ChannelDTOLazy;
import evolution.dto.model.ChannelSaveDTO;
import evolution.dto.model.ChannelUpdateDTO;
import evolution.model.User;
import evolution.model.channel.Channel;
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

    void clearRowByUserForeignKey(Long userId);

    List<Channel> findChannelForWhoCreateChannelUser(Long userId);

    List<Channel> findChannelForWhoCreateChannelUser(Long userId, String sortType, List<String> sortProperties);

    Page<Channel> findChannelForWhoCreateChannelUser(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties);

    Long findCountUserByChannelId(Long id);

    List<User> findUserByChannel(Long channelId);

    List<User> findUserByChannel(Long channelId, String sortType, List<String> sortProperties);

    Page<User> findUserByChannel(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);
}
