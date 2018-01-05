package evolution.crud;

import evolution.crud.api.ChannelCrudManagerService;
import evolution.model.User;
import evolution.model.channel.Channel;
import evolution.repository.ChannelRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelCrudManagerServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Optional<Channel> findOne(Long id) {
        return channelRepository.findOneChannel(id);
    }

    @Override
    public Channel save(Channel object) {
        return null;
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
    public List<User> findUserByChannelName(String name) {
        Optional<Channel> op = channelRepository.findChannelByNameLazyChannelUser(name);
        if (op.isPresent()) {
            return op.get().getChannelUser();
        } else {
            return new ArrayList<>();
        }
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
    @Transactional
    public Channel initializeLazy(Channel channel) {
        Hibernate.initialize(channel.getChannelUser());
        Hibernate.initialize(channel.getMessageChannelList());
        Hibernate.initialize(channel.getWhoCreatedChannel());
        return channel;
    }
}
