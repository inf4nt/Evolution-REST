package evolution.crud.api;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface AbstractCrudManagerService<Object, PK> {

    List<Object> findAll();

    List<Object> findAll(String sort, List<String> sortProperties);

    Page<Object> findAll(Integer page, Integer size, String sort, List<String> sortProperties);

    Optional<Object> findOne(PK pk);

    Object save(Object object);

    void delete(PK pk);
}
