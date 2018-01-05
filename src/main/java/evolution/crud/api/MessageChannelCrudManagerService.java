package evolution.crud.api;


import evolution.model.channel.MessageChannel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MessageChannelCrudManagerService extends AbstractCrudManagerService<MessageChannel, Long>, PageableManager {

    List<MessageChannel> findMessageChannelByChannelId(Long channelId);

    List<MessageChannel> findMessageChannelByChannelId(Long channelId, String sortType, List<String> sortProperties);

    Page<MessageChannel> findMessageChannelByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageChannel> findMessageChannelBySender(Long senderId);

    List<MessageChannel> findMessageChannelBySender(Long senderId, String sortType, List<String> sortProperties);

    Page<MessageChannel> findMessageChannelBySender(Long senderId, Integer page, Integer size, String sortType, List<String> sortProperties);

    List<MessageChannel> findMessageChannelByChannelName(String name);

    List<MessageChannel> findMessageChannelByChannelName(String name, String sortType, List<String> sortProperties);

    Page<MessageChannel> findMessageChannelByChannelName(String name, Integer page, Integer size, String sortType, List<String> sortProperties);

    Optional<Long> findCountMessageChannelByChannelId(Long id);

    Optional<Long> findCountMessageChannelByChannelName(String name);
}
