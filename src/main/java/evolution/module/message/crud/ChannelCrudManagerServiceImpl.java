package evolution.message.crud;

import evolution.message.crud.api.ChannelCrudManagerService;
import evolution.message.crud.api.MessageChannelCrudManagerService;
import evolution.user.crud.api.UserCrudManagerService;
import evolution.message.dto.ChannelSaveDTO;
import evolution.user.model.User;
import evolution.message.model.Channel;
import evolution.message.model.MessageChannel;
import evolution.message.repository.ChannelRepository;
import evolution.service.DateService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ChannelCrudManagerServiceImpl implements ChannelCrudManagerService {

    @Value("${model.channel.maxfetch}")
    private Integer defaultMaxFetch;

    @Value("${model.channel.defaultsort}")
    private String defaultSortType;

    @Value("${model.channel.defaultsortproperties}")
    private String defaultSortProperties;

    @PersistenceContext
    private EntityManager entityManager;

    private final ChannelRepository channelRepository;

    private final UserCrudManagerService userCrudManagerService;

    private final MessageChannelCrudManagerService messageChannelCrudManagerService;

    private final DateService dateService;

    @Autowired
    public ChannelCrudManagerServiceImpl(ChannelRepository channelRepository,
                                         UserCrudManagerService userCrudManagerService,
                                         MessageChannelCrudManagerService messageChannelCrudManagerService,
                                         DateService dateService) {
        this.channelRepository = channelRepository;
        this.userCrudManagerService = userCrudManagerService;
        this.messageChannelCrudManagerService = messageChannelCrudManagerService;
        this.dateService = dateService;
    }

    @Override
    public Optional<Channel> findOne(Long id) {
        return channelRepository.findOneChannel(id);
    }

    @Override
    public Channel save(Channel object) {
        return channelRepository.save(object);
    }

    @Override
    @Transactional
    public void delete(Long aLong) {
        Optional<Channel> op = findOneLazy(aLong);
        op.ifPresent(channelRepository::delete);
    }

    @Override
    @Transactional
    public Optional<Channel> findOneLazy(Long id) {
        Optional<Channel> op = channelRepository.findOneChannel(id);
        op.ifPresent(this::initializeLazy);
        return op;
    }

    @Override
    public List<Channel> findByName(String name) {
        return channelRepository.findChannelByName(name);
    }

    @Override
    public List<Channel> findByName(String name, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelByName(name, s);
    }

    @Override
    public Page<Channel> findByName(String name, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelByName(name, p);
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<Channel> findAll(String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findAll(s);
    }

    @Override
    public Page<Channel> findAll(Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findAll(p);
    }

    @Override
    public List<Channel> findAllLazy() {
        return initializeLazy(findAll());
    }

    @Override
    public List<Channel> findAllLazy(String sortType, List<String> sortProperties) {
        return initializeLazy(findAll(sortType, sortProperties));

    }

    @Override
    public Page<Channel> findAllLazy(Integer page, Integer size, String sortType, List<String> sortProperties) {
        return initializeLazy(findAll(page, size, sortType, sortProperties));
    }

    @Override
    public List<Channel> findChannelForChannelUser(Long userId) {
        return channelRepository.findChannelForChannelUser(userId);
    }

    @Override
    public List<Channel> findChannelForChannelUser(Long userId, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelForChannelUser(userId, s);
    }

    @Override
    public Page<Channel> findChannelForChannelUser(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelForChannelUser(userId, p);
    }

    @Override
    public List<User> findUserByChannelId(Long id) {
        Optional<Channel> op = channelRepository.findChannelByIdLazyChannelUser(id);
        if (op.isPresent()) {
            return op.get().getChannelUser();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Channel> findChannelByIdLazyChannelUser(Long id) {
        return channelRepository.findChannelByIdLazyChannelUser(id);
    }

    @Override
    @Transactional
    public void clearRowByUserForeignKey(Long userId) {
        List<Channel> list = channelRepository.findChannelForChannelUser(userId);
        list.forEach(o -> delete(o.getId()));

        List<Channel> listWhoCreated = channelRepository.findChannelForWhoCreateChannelUser(userId);
        listWhoCreated.forEach(o -> delete(o.getId()));
    }

    @Override
    public List<Channel> findChannelForWhoCreateChannelUser(Long userId) {
        return channelRepository.findChannelForWhoCreateChannelUser(userId);
    }

    @Override
    public List<Channel> findChannelForWhoCreateChannelUser(Long userId, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelForWhoCreateChannelUser(userId, s);
    }

    @Override
    public Page<Channel> findChannelForWhoCreateChannelUser(Long userId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return channelRepository.findChannelForWhoCreateChannelUser(userId, p);
    }

    @Override
    public Long countUserByChannel(Long id) {
        return channelRepository.countUserByChannel(id);
    }

    @Override
    @Transactional
    public List<User> findUserByChannel(Long channelId) {
        Optional<Channel> oc = findOne(channelId);
        if (oc.isPresent()) {
            return oc.get().getChannelUser();
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> findUserByChannel(Long channelId, String sortType, List<String> sortProperties) {
        Sort s = getSortForRestService(sortType, sortProperties,
                this.defaultSortType, this.defaultSortProperties);
        return null;
    }

    @Override
    public Page<User> findUserByChannel(Long channelId, Integer page, Integer size, String sortType, List<String> sortProperties) {
        Pageable p = getPageableForRestService(page, size, sortType, sortProperties,
                this.defaultMaxFetch, this.defaultSortType, this.defaultSortProperties);
        return null;
    }

    @Override
    @Transactional
    public Optional<Channel> createNewChannel(ChannelSaveDTO channelSaveDTO) {
        Optional<User> ou = userCrudManagerService.findOne(channelSaveDTO.getWhoCreateId());
        if (!ou.isPresent()) {
            return Optional.empty();
        }

        Channel channel = new Channel();

        channel.getChannelUser()
                .add(ou.get());

        channel.setWhoCreatedChannel(ou.get());
        channel.setPrivate(channelSaveDTO.isPrivate());
        channel.setDateCreate(dateService.getCurrentDateInUTC());
        channel.setChannelName(channelSaveDTO.getChannelName());
        channel.setActive(true);

        MessageChannel messageChannel = new MessageChannel();
        messageChannel.setText(ou.get().getFirstName() + " " + ou.get().getLastName() + " create channel #" + channelSaveDTO.getChannelName());
        messageChannel.setDatePost(dateService.getCurrentDateInUTC());
        messageChannel.setChannel(channel);
        messageChannel.setActive(true);
        messageChannel.setSender(ou.get());

        channel.getMessageChannelList()
                .add(messageChannel);

        return Optional.of(save(channel));
    }

    @Override
    public Optional<Channel> createNewChannel(String channelName, Long whoCreateChannelId, boolean isPrivate) {
        return createNewChannel(new ChannelSaveDTO(channelName, isPrivate, whoCreateChannelId));
    }

    @Override
    @Transactional
    public Optional<Channel> joinChannel(Long channelId, Long userId) {
        Optional<Channel> oc = findOne(channelId);
        if (!oc.isPresent()) {
            return Optional.empty();
        }
        Optional<User> ou = userCrudManagerService.findOne(userId);
        if (!ou.isPresent()) {
            detach(oc.get());
            return Optional.empty();
        }
        MessageChannel m = new MessageChannel();

        oc.get().getChannelUser().add(ou.get());

        m.setSender(ou.get());
        m.setChannel(oc.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(ou.get().getFirstName() + " " + ou.get().getLastName() + " join to channel #" + oc.get().getChannelName());
        oc.get().getMessageChannelList().add(m);
        return Optional.of(save(oc.get()));
    }

    @Override
    @Transactional
    public Optional<Channel> outFromChannel(Long channelId, Long userId) {
        Optional<User> ou = userCrudManagerService.findOne(userId);
        if (!ou.isPresent()) {
            return Optional.empty();
        }

        Optional<Channel> oc = findOne(channelId);
        if (!oc.isPresent()) {
            userCrudManagerService.detach(ou.get());
            return Optional.empty();
        }

        oc.get().getChannelUser().remove(ou.get());
        MessageChannel m = new MessageChannel();
        m.setSender(ou.get());
        m.setChannel(oc.get());
        m.setActive(true);
        m.setDatePost(dateService.getCurrentDateInUTC());
        m.setText(ou.get().getFirstName() + " " + ou.get().getLastName() + " out from channel #" + oc.get().getChannelName());
        oc.get().getMessageChannelList().add(m);

        return Optional.of(channelRepository.save(oc.get()));
    }

    @Override
    public void detach(Channel channel) {
        entityManager.detach(channel);
    }

    @Override
    @Transactional
    public Channel initializeLazy(Channel channel) {
        Hibernate.initialize(channel.getChannelUser());
        Hibernate.initialize(channel.getMessageChannelList());
        Hibernate.initialize(channel.getWhoCreatedChannel());
        return channel;
    }
}
