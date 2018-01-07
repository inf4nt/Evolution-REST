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

    Long findCountMessageChannelByChannelId(Long id);

//    MessageChannel save(String text, Long senderId, Long channelId);
}
