package evolution.crud.api;


import evolution.model.channel.MessageChannel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface MessageChannelCrudManagerService extends AbstractCrudManagerService<MessageChannel, Long>, PageableManager {

    List<MessageChannel> findMessageChannelByChannelId(Long channelId);

    List<MessageChannel> findMessageChannelByChannelId(Long channelId, String sortType, List<String> sortProperties);

    Page<MessageChannel> findMessageChannelByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties);

    CompletableFuture<List<MessageChannel>> findMessageChannelBySenderAsync(Long senderId);

    List<MessageChannel> findMessageChannelBySender(Long senderId);

    List<MessageChannel> findMessageChannelBySender(Long senderId, String sortType, List<String> sortProperties);

    Page<MessageChannel> findMessageChannelBySender(Long senderId, Integer page, Integer size, String sortType, List<String> sortProperties);

    Long findCountMessageChannelByChannelId(Long id);

    void deleteByIdAndSenderId(Long id, Long senderId);

    void delete(List<MessageChannel> messageChannels);

//    MessageChannel save(String text, Long senderId, Long channelId);
}
