package evolution.crud;

import evolution.crud.api.FeedCrudManagerService;
import evolution.model.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 08.11.2017.
 */
@Service
public class FeedCrudManagerServiceImpl implements FeedCrudManagerService {
    @Override
    public List<Feed> findAll() {
        return null;
    }

    @Override
    public List<Feed> findAll(String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Page<Feed> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Optional<Feed> findOne(Long aLong) {
        return null;
    }

    @Override
    public Feed save(Feed object) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Pageable getPageable(Integer page, Integer size, String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Pageable getPageable(Integer page, Integer size) {
        return null;
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        return null;
    }
}
