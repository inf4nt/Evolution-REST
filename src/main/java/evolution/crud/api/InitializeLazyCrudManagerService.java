package evolution.crud.api;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InitializeLazyCrudManagerService<T> {

    @Transactional
    T initializeLazy(T t);

    @Transactional
    default List<T> initializeLazy(List<T> list) {
        list.forEach(this::initializeLazy);
        return list;
    }

    @Transactional
    default Page<T> initializeLazy(Page<T> page) {
        return page.map(this::initializeLazy);
    }

    @Transactional
    default Optional<T> initializeLazy(Optional<T> optional) {
        return optional.map(this::initializeLazy);
    }

}
