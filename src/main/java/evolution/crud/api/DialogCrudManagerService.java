package evolution.crud.api;

import evolution.dto.modelOld.MessageDTO;
import evolution.model.Dialog;
import evolution.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Infant on 07.11.2017.
 */
public interface DialogCrudManagerService extends AbstractCrudManagerService<Dialog, Long>, PageableManager {

    Optional<Dialog> findOneLazy(Long id);

    Optional<Dialog> findOneLazyAndParticipantId(Long id, Long participant);

    List<Dialog> findAllLazy();

    List<Dialog> findAllLazy(String sort, List<String> sortProperties);

    Page<Dialog> findAllLazy(Integer page, Integer size, String sort, List<String> sortProperties);

    Page<Dialog> findMyDialog(Long iam, Integer page, Integer size, String sort, List<String> sortProperties);

    List<Dialog> findMyDialog(Long iam, String sort, List<String> sortProperties);

    List<Dialog> findDialogsByUserId(Long userId);

    Optional<Dialog> findOne(Long iam, Long dialogId);

    boolean deleteById(Long id);

    void deleteAllDialogRowByUser(Long id);
}
