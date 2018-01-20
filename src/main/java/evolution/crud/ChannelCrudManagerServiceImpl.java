package evolution.crud;

import evolution.crud.api.ChannelCrudManagerService;
import evolution.crud.api.UserCrudManagerService;
import evolution.dto.model.ChannelSaveDTO;
import evolution.model.User;
import evolution.model.channel.Channel;
import evolution.model.channel.ChannelUserReference;
import evolution.repository.ChannelRepository;
import evolution.repository.ChannelUserReferenceRepository;
import evolution.service.DateService;
import org.hibernate.Hibernate;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserCrudManagerService userCrudManagerService;

    @Autowired
    private DateService dateService;

    @Autowired
    private ChannelUserReferenceRepository channelUserReferenceRepository;

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
    public List<Channel> findChannelForWhoCreateChannelUserLazy(Long userId) {
        List<Channel> list =  channelRepository.findChannelForWhoCreateChannelUser(userId);
        initializeLazy(list);
        return list;
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
        CompletableFuture<Optional<User>> cu = userCrudManagerService.findOneAsync(channelSaveDTO.getWhoCreateId());
        CompletableFuture.allOf(cu);

        Optional<User> ou = cu.join();

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

        return Optional.of(save(channel));
    }

    @Override
    public Optional<Channel> createNewChannel(String channelName, Long whoCreateChannelId, boolean isPrivate) {
        return createNewChannel(new ChannelSaveDTO(channelName, isPrivate, whoCreateChannelId));
    }

    @Override
    @Transactional
    public Optional<Channel> joinChannel(Long channelId, Long userId) {
        CompletableFuture<Optional<User>> cs = userCrudManagerService.findOneAsync(userId);
        CompletableFuture<Optional<Channel>> cc = findOneAsync(channelId);

        CompletableFuture.allOf(cs, cc);

        Optional<User> sender = cs.join();
        Optional<Channel> channel = cc.join();

        if (!sender.isPresent() || !channel.isPresent()) {
            sender.ifPresent(v -> userCrudManagerService.detach(v));
            channel.ifPresent(v -> detach(v));
            return Optional.empty();
        }

        ChannelUserReference channelUserReference = channelUserReferenceRepository.save(new ChannelUserReference(channel.get(), sender.get()));
        return Optional.of(channelUserReference.getPk().getChannel());
    }

    @Override
    @Transactional
    public CompletableFuture<Optional<Channel>> findOneAsync(Long id) {
        return channelRepository.findOneAsync(id)
                .thenApply(v -> Optional.ofNullable(v));
    }

    @Override
    @Transactional
    public Optional<Channel> outFromChannel(Long channelId, Long userId) {
        Optional<ChannelUserReference> cur = channelUserReferenceRepository.findByUserAndChannel(channelId, userId);
        if (cur.isPresent()) {
            channelUserReferenceRepository.delete(cur.get());
            return Optional.of(cur.get().getPk().getChannel());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void detach(Channel channel) {
        entityManager.detach(channel);
    }

    @Override
    public CompletableFuture<List<Channel>> findMyChannelAsync(Long userid) {
        return channelRepository.findMyChannelAsync(userid);
    }

    @Override
    public void delete(List<Channel> list) {
        channelRepository.delete(list);
    }

    @Override
    public CompletableFuture<List<Channel>> findByWhoCreatedChannelAsync(Long whoCreatedChannel) {
        return channelRepository.findByWhoCreatedChannelAsync(whoCreatedChannel);
    }

    @Override
    public List<Channel> findByWhoCreatedChannel(Long whoCreatedChannel) {
        return channelRepository.findByWhoCreatedChannel(whoCreatedChannel);
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
