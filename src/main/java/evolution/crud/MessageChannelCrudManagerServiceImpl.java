package evolution.crud;

import evolution.crud.api.MessageChannelCrudManagerService;
import evolution.model.channel.MessageChannel;
import evolution.repository.MessageChanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessageChannelCrudManagerServiceImpl implements MessageChannelCrudManagerService {

    @Value("${model.messageChannel.maxfetch}")
    private Integer defaultMaxFetch;

    @Value("${model.messageChannel.defaultsort}")
    private String defaultSortType;

    @Value("${model.messageChannel.defaultsortproperties}")
    private String defaultSortProperties;

    private final MessageChanelRepository messageChanelRepository;

    @Autowired
    public MessageChannelCrudManagerServiceImpl(MessageChanelRepository messageChanelRepository) {
        this.messageChanelRepository = messageChanelRepository;
    }

    @Override
    public List<MessageChannel> findMessageChannelByChannelId(Long channelId) {
        return messageChanelRepository.findMessageChannelByChannelId(channelId);
    }

    @Override
    public List<MessageChannel> findMessageChannelByChannelId(Long channelId, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findMessageChannelByChannelId(channelId, s);
    }

    @Override
    public Page<MessageChannel> findMessageChannelByChannelId(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findMessageChannelByChannelId(channelId, p);
    }

    @Override
    public List<MessageChannel> findMessageChannelBySender(Long senderId) {
        return messageChanelRepository.findMessageChannelBySender(senderId);
    }

    @Override
    public List<MessageChannel> findMessageChannelBySender(Long senderId, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findMessageChannelBySender(senderId, s);
    }

    @Override
    public Page<MessageChannel> findMessageChannelBySender(Long senderId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findMessageChannelBySender(senderId, p);
    }

    @Override
    public Long findCountMessageChannelByChannelId(Long id) {
        return messageChanelRepository.findCountMessageChannelByChannelId(id);
    }

    @Override
    public List<MessageChannel> findAll() {
        return messageChanelRepository.findAll();
    }

    @Override
    public List<MessageChannel> findAll(String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findAll(s);
    }

    @Override
    public Page<MessageChannel> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return messageChanelRepository.findAll(p);
    }

    @Override
    public Optional<MessageChannel> findOne(Long aLong) {
        return messageChanelRepository.findOneMessageChannel(aLong);
    }

    @Override
    public MessageChannel save(MessageChannel object) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        Optional<MessageChannel> op = messageChanelRepository.findOneMessageChannel(aLong);
        op.ifPresent(messageChanelRepository::delete);
    }
}
