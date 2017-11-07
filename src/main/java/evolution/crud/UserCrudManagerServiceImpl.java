package evolution.crud;

import evolution.crud.api.UserCrudManagerService;
import evolution.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
@Service
public class UserCrudManagerServiceImpl implements UserCrudManagerService {

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAll(String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String sort, List<String> sortProperties) {
        return null;
    }

    @Override
    public Optional<User> findOne(Long aLong) {
        return null;
    }

    @Override
    public User save(User object) {
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
//        return getPageableForRestService(page, size,
//                this.userMaxFetch);
        return null;
    }

    @Override
    public Sort getSort(String sort, List<String> sortProperties) {
        return null;
    }
}
