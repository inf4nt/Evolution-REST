package evolution.crud.api;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InitializeLazyCrudManagerService<Object> {

    @Transactional
    Object initializeLazy(Object object);

    @Transactional
    default List<Object> initializeLazy(List<Object> list) {
        list.forEach(this::initializeLazy);
        return list;
    }

    @Transactional
    default Page<Object> initializeLazy(Page<Object> page) {
        page.map(this::initializeLazy);
        return page;
    }

    @Transactional
    default Optional<Object> initializeLazy(Optional<Object> optional) {
        optional.map(this::initializeLazy);
        return optional;
    }

}
