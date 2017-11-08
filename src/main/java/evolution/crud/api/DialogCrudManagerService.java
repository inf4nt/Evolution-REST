package evolution.crud.api;

import evolution.model.Dialog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface DialogCrudManagerService extends AbstractCrudManagerService<Dialog, Long>, PageableManager {

    Page<Dialog> findMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Dialog> findMyDialog(Long iam, String sort, List<String> sortProperties);

    Optional<Dialog> findOne(Long iam, Long dialogId);
}