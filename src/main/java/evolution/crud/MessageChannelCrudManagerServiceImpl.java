package evolution.crud;

import evolution.crud.api.ChannelCrudManagerService;
import evolution.crud.api.MessageChannelCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.model.MessageChannelSaveDTO;
import evolution.model.User;
import evolution.model.channel.Channel;
import evolution.model.channel.MessageChannel;
import evolution.repository.MessageChanelRepository;
import evolution.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageChannelCrudManagerServiceImpl implements MessageChannelCrudManagerService {

    @Value("${model.messageChannel.maxfetch}")
    private Integer defaultMaxFetch;

    @Value("${model.messageChannel.defaultsort}")
    private String defaultSortType;

    @Value("${model.messageChannel.defaultsortproperties}")
    private String defaultSortProperties;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DateService dateService;

    @Autowired
    private MessageChanelRepository messageChanelRepository;

    @Autowired
    private UserCrudManagerService userCrudManagerService;

    @Autowired
    private ChannelCrudManagerService channelCrudManagerService;

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
    public CompletableFuture<List<MessageChannel>> findMessageChannelBySenderAsync(Long senderId) {
        return messageChanelRepository.findMessageChannelBySenderAsync(senderId);
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
        return messageChanelRepository.save(object);
    }

    @Override
    @Transactional
    public Optional<MessageChannel> save(MessageChannelSaveDTO messageChannelSaveDTO) {
        return save(messageChannelSaveDTO.getChannelId(), messageChannelSaveDTO.getSenderId(), messageChannelSaveDTO.getText());
    }

    @Override
    @Transactional
    public Optional<MessageChannel> save(Long channelId, Long senderId, String text) {
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(senderId);
        CompletableFuture<Optional<Channel>> cc = channelCrudManagerService.findOneAsync(channelId);

        CompletableFuture.allOf(cs, cc);

        Optional<User> sender = cs.join();
        Optional<Channel> channel = cc.join();

        if (!sender.isPresent() || !channel.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            channel.ifPresent(v -> detach(v));
            return Optional.empty();
        }

        MessageChannel m = new MessageChannel();
        m.setSender(sender.get());
        m.setChannel(channel.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(text);
        return Optional.of(messageChanelRepository.save(m));
    }

    @Override
    public Optional<MessageChannel> sendMessageAfterLeftFromChannel(Long channelId, Long senderId) {
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(senderId);
        CompletableFuture<Optional<Channel>> cc = channelCrudManagerService.findOneAsync(channelId);

        CompletableFuture.allOf(cs, cc);

        Optional<User> sender = cs.join();
        Optional<Channel> channel = cc.join();

        if (!sender.isPresent() || !channel.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            channel.ifPresent(v -> detach(v));
            return Optional.empty();
        }

        MessageChannel m = new MessageChannel();
        m.setSender(sender.get());
        m.setChannel(channel.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(sender.get().getFirstName() + " " + sender.get().getLastName() + " out from channel #" + channel.get().getChannelName());
        return Optional.of(messageChanelRepository.save(m));
    }


    @Override
    public Optional<MessageChannel> sendMessageAfterJoinFromChannel(Long channelId, Long senderId) {
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(senderId);
        CompletableFuture<Optional<Channel>> cc = channelCrudManagerService.findOneAsync(channelId);

        CompletableFuture.allOf(cs, cc);

        Optional<User> sender = cs.join();
        Optional<Channel> channel = cc.join();

        if (!sender.isPresent() || !channel.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            channel.ifPresent(v -> detach(v));
            return Optional.empty();
        }

        MessageChannel m = new MessageChannel();
        m.setSender(sender.get());
        m.setChannel(channel.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(sender.get().getFirstName() + " " + sender.get().getLastName() + " join to channel #" + channel.get().getChannelName());
        return Optional.of(messageChanelRepository.save(m));
    }

    @Override
    public Optional<MessageChannel> sendMessageAfterCreateChannel(Long channelId, Long whoCreateChannel) {
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(whoCreateChannel);
        CompletableFuture<Optional<Channel>> cc = channelCrudManagerService.findOneAsync(channelId);

        CompletableFuture.allOf(cs, cc);

        Optional<User> sender = cs.join();
        Optional<Channel> channel = cc.join();

        if (!sender.isPresent() || !channel.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            channel.ifPresent(v -> detach(v));
            return Optional.empty();
        }

        MessageChannel m = new MessageChannel();
        m.setSender(sender.get());
        m.setChannel(channel.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(sender.get().getFirstName() + " " + sender.get().getLastName() + " create channel #" + channel.get().getChannelName());
        return Optional.of(messageChanelRepository.save(m));
    }

    @Override
    @Transactional
    public void deleteByIdAndSenderId(Long id, Long senderId) {
        Optional<MessageChannel> mc = messageChanelRepository.findOneMessageChannel(id, senderId);
        mc.ifPresent(messageChanelRepository::delete);
    }

    @Override
    public void delete(List<MessageChannel> messageChannels) {
        messageChanelRepository.delete(messageChannels);
    }

    @Override
    @Transactional
    public void delete(Long aLong) {
        Optional<MessageChannel> op = messageChanelRepository.findOneMessageChannel(aLong);
        op.ifPresent(messageChanelRepository::delete);
    }

    @Override
    public void detach(Channel channel) {
        entityManager.detach(channel);
    }
}
